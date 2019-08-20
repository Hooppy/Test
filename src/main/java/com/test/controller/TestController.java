package com.test.controller;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.pdfbox.io.IOUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.test.dao.TestDao;
import com.test.handler.PdfCreator;

@Controller
public class TestController{
	
	@Autowired
	TestDao testDao;

	@Autowired
	PdfCreator pdfCreator;
	
	@RequestMapping("welcome")
	public String Hello(ModelMap model, 
			HttpSession httpSession) {
		
		// Session 에 값 할당하여 사용
		model.addAttribute("msg", "HELLO");
		model.addAttribute("username", httpSession.getAttribute("username"));
		model.addAttribute("password", httpSession.getAttribute("password"));
		
		String username = (String) httpSession.getAttribute("username");
		
		try {
			model.addAttribute("bldname", testDao.read(username).getBldname());
			model.addAttribute("addr", testDao.read(username).getAddr());
			model.addAttribute("name", testDao.read(username).getName());
			model.addAttribute("code", testDao.read(username).getCode());
			
			model.addAttribute("miscode", testDao.pdf(username).getCode());
			model.addAttribute("totoname", testDao.pdf(username).getTotoname());
			model.addAttribute("totoaddr", testDao.pdf(username).getTotoaddr());
			model.addAttribute("contractor", testDao.pdf(username).getContractor());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "index";
	}
	
	@RequestMapping("new")
	public String New(ModelMap model) {
		
		model.addAttribute("msg", "NEW");
		
		return "new";
	}
	
	@RequestMapping("box")
	public String Box(ModelMap model) {
		
		PDDocument p = extractingText("E:\\workspace\\test\\src\\main\\webapp\\resources\\A.pdf");
		System.out.println(p.getDocument());
		
		model.addAttribute("msg", "BOX");
		
		return "box";
	}
	
	public PDDocument extractingText(String targetFile) {
		System.out.println("1");
        boolean result = true;

        String extractTxtFile = targetFile + ".txt";
        PDDocument pdDocument = null;
        
        File file = new File(targetFile);

        try {
        	System.out.println("2");
        	pdDocument = PDDocument.load(file);
        } catch (IOException e) {
        	e.printStackTrace();
        }
        FileOutputStream fileOutputStream = openOutputStream(new File(extractTxtFile));
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
        BufferedWriter bufferedWriter = null;
        bufferedWriter = new BufferedWriter(outputStreamWriter);
        PDFTextStripper stripper = null;
        
        try {
        	System.out.println("3");
        	stripper = new PDFTextStripper();
        } catch (IOException e) {
        	System.out.println("TextExtraction-extractingText ERROR: PDFTextStripper 객체생성 실패");
        }
        stripper.setStartPage(1);
        //stripper.setEndPage(11);
        try {
        	System.out.println("4");
        	stripper.writeText(pdDocument, bufferedWriter);
        } catch (IOException e) {
        	System.out.println("TextExtraction-extractingText ERROR: text 추출 실패");
        }
        try {
        	System.out.println("5");
        	pdDocument.close();
        } catch (IOException e) {
        	System.out.println("TextExtraction-extractingText ERROR: PDDocument close 실패");
        }
        IOUtils.closeQuietly(bufferedWriter);
        return pdDocument;
	}
	
	public static FileOutputStream openOutputStream(File file) {
		FileOutputStream fileOutputStream = null;

        try {
        	System.out.println("6");
        	fileOutputStream = new FileOutputStream(file);
        } catch (Exception e) {
        	System.out.println("TextExtraction-openOutputStream ERROR: " + e.getMessage());
        }
        return fileOutputStream;
	}
	
	@RequestMapping(value = "create", method = RequestMethod.POST)
	public String Create(ModelMap model,
			@RequestParam(value = "username") String username,
			@RequestParam(value = "password") String password,
			@RequestParam(value = "bldname") String bldname,
			@RequestParam(value = "addr") String addr,
			@RequestParam(value = "name") String name,
			RedirectAttributes ra) {
		
		String move = "";
		
		ra.addFlashAttribute("message", "저장됐습니다.");
		
		if(testDao.isExist(username)) {
			move = "new";
		}else {
			testDao.create(username, password, bldname, addr, name);
			move = "login";
		}
		
		return "redirect:/" + move;
	}
	
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public String Delete(ModelMap model,
			@RequestParam(value = "username") String username,
			@RequestParam(value = "password") String password) {
		
		testDao.delete(username, password);
		
		return "login";
	}
	
	@RequestMapping("login")
	public String Login(ModelMap model,
			HttpSession session) {
		
		Object failureCode = session.getAttribute("failureCode");
		
		model.addAttribute("failureCode", failureCode);
		model.addAttribute("msg", "LOGIN");
		
		return "login";
	}
	
	/*@RequestMapping("login/{result:success|fail}")
	public String LoginFail(ModelMap model,
			HttpSession session,
			@PathVariable("result") String result) {
		
		if("success".equals(result)){
			
		}else {
			Object failureCode = session.getAttribute("failureCode");
			
			model.addAttribute("failureCode", failureCode);
		}
		
		return "login";
	}*/
	
	@RequestMapping("logouts")
	public String Logout(ModelMap model) {
		
		model.addAttribute("msg", "LOGOUT");
		
		
		return "logout";
	}
	
	@RequestMapping("error")
	public String Error(ModelMap model) {
		
		model.addAttribute("msg", "ERROR");
		
		return "error";
	}
	
	@RequestMapping("downloadPDF")
	public void downloadPDF(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "username") String username,
			@RequestParam(value = "bldname") String bldname,
			@RequestParam(value = "addr") String addr,
			@RequestParam(value = "name") String name,
			@RequestParam(value = "code") String code
			) throws IOException {
		
		final ServletContext servletContext = request.getSession().getServletContext();
		final File tempDirectory = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
		final String temperoryFilePath = tempDirectory.getAbsolutePath();
		
		String fileName = "MyDoc.pdf";
		response.setContentType("application/pdf");
		response.setHeader("Content-disposition", "attachment; filename=" + fileName);
		
		try {
			pdfCreator.createPDF(temperoryFilePath + "\\" + fileName, username, code, bldname, addr, name);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			baos = convertPDFToByteArrayOutputStream(temperoryFilePath + "\\" + fileName);
			OutputStream os = response.getOutputStream();
			baos.writeTo(os);
			os.flush();
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public ByteArrayOutputStream convertPDFToByteArrayOutputStream(String fileName) {
		
		InputStream inputStream = null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			inputStream = new FileInputStream(fileName);
			byte[] buffer = new byte[1024];
			baos = new ByteArrayOutputStream();
			
			int bytesRead;
			while((bytesRead = inputStream.read(buffer)) != -1) {
				baos.write(buffer, 0, bytesRead);
			}
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}catch(IOException e) {
			e.printStackTrace();
		}finally {
			if(inputStream != null) {
				try {
					inputStream.close();
				}catch(IOException e) {
					e.printStackTrace();
				}
			}
		}
		return baos;
	}
}

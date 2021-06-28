package com.ds.soap.service;

import java.io.InputStream;
import java.math.BigInteger;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ext.CoreXMLSerializers.XMLGregorianCalendarSerializer;

import de.tekup.soap.models.whitetest.Exam;
import de.tekup.soap.models.whitetest.ObjectFactory;
import de.tekup.soap.models.whitetest.Student;
import de.tekup.soap.models.whitetest.StudentRequest;
import de.tekup.soap.models.whitetest.WhiteTestResponse;

@Service
public class WhiteTestService {

	List<Student> students = new ArrayList<Student>();
	List<Exam> exams = new ArrayList<>();
	List<String> ERROR = new ArrayList<>();
	List<Date> dates = new ArrayList<>();
	int stnotfound = 0, exnotfound = 0;
	
	
	// Convert XMLGregorianCalendar To LocalDate
	public static final XMLGregorianCalendar getDate(Date d) {
	    try {
	        return DatatypeFactory.newInstance().newXMLGregorianCalendar(new SimpleDateFormat("yyyy-MM-dd").format(d));
	    } catch (DatatypeConfigurationException e) {
	        return null;
	    }
	}

	public WhiteTestResponse foundWhitetTest(StudentRequest request) {

		WhiteTestResponse response = new ObjectFactory().createWhiteTestResponse();

		// add students to the list
		Student st1 = new Student();st1.setId(1);st1.setName("hatem");st1.setAddress("ben arous");students.add(st1);
		Student st2 = new Student();st2.setId(2);st2.setName("haythem");st2.setAddress("mourouj");students.add(st2);
		Student st3 = new Student();st3.setId(3);st3.setName("ahmed");st3.setAddress("mahdia");students.add(st3);
		/////////////////////////////
		// add exam to the list
		 Exam ex1 = new Exam();ex1.setCode("PROG");ex1.setName("OCA");exams.add(ex1);
		 Exam ex2 = new Exam();ex2.setCode("SYS");ex2.setName("RED HAT");exams.add(ex2);
		 Exam ex3 = new Exam();ex3.setCode("WEB");ex3.setName("HTML & CSS");exams.add(ex3);
		///////////////////////////////
		 // Get the date
		 Date date1 = Date.valueOf(LocalDate.now().minusMonths(2));dates.add(date1);
		 Date date2 = Date.valueOf(LocalDate.now().minusMonths(1));dates.add(date2);
		 Date date3 = Date.valueOf(LocalDate.now().minusDays(15));dates.add(date3);
		 

		// ############# GET STUDENT INFO ##############//
		for (int i = 0; i < students.size(); i++) {
			if (request.getStudentId() == students.get(i).getId()) {
				response.setStudent(students.get(i));
			} else {
				stnotfound++;
			}
		}
		// ##############################################//

		// ############# GET EXAM INFO ##############//
		for (int i = 0; i < exams.size(); i++) {
			if (request.getExamCode().equalsIgnoreCase(exams.get(i).getCode())) {
				response.setExam(exams.get(i));
				response.setDate(getDate(dates.get(i)));
				
			} else {
				exnotfound++;
			}
		}
		// ##############################################//

		// ############## GET ERROR ###################//
		if (stnotfound == students.size()) {
			ERROR.add("wrong student id");
		}
		if (exnotfound == exams.size())
			ERROR.add("request exam not found");
		
		if (ERROR.size() != 0) {
			// kifh bech najouti el error in xsd file
		}

		// #############################################//

		return response;
	}

}

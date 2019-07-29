package com.glosys.lmsweb;

import com.glosys.lms.Course;
import com.glosys.lms.CourseCategory;
import com.glosys.lms.controller.CourseCategoryController;
import com.glosys.lms.controller.CourseController;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/addCourse")

public class AddCourseServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        CourseCategoryController courseCategoryController = new CourseCategoryController();
        List<CourseCategory> courseCategories = courseCategoryController.getCourseCategories();
        Course course = new Course("","","",new CourseCategory(5),false,false,false,false);
        PrintWriter out = response.getWriter();
        out.println(showPage(course,courseCategories));
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        CourseController courseController = new CourseController();

        String courseName = request.getParameter("courseName");
        String courseCode = request.getParameter("courseCode");
        String syllabus = request.getParameter("syllabus");
        Integer courseCategoryId = Integer.parseInt(request.getParameter("cc"));
        boolean workshopEligibility = Boolean.parseBoolean(request.getParameter("workshop_eligibility"));
        boolean inplantTrainingEligibility = Boolean.parseBoolean(request.getParameter("inplant_training_eligibility"));
        boolean corporateTrainingEligibility = Boolean.parseBoolean(request.getParameter("corporate_training_eligibility"));
        boolean researchTrainingEligibility = Boolean.parseBoolean(request.getParameter("research_training_eligibility"));

        Course course = new Course(courseName,
                courseCode,
                syllabus,
                new CourseCategory(courseCategoryId),
                workshopEligibility,
                researchTrainingEligibility,
                inplantTrainingEligibility,
                corporateTrainingEligibility);

        CourseCategoryController courseCategoryController = new CourseCategoryController();
        List<CourseCategory> courseCategories = courseCategoryController.getCourseCategories();


        if(courseController.isExistingCourse(courseCode)){
            PrintWriter out = response.getWriter();
            request.setAttribute("isExistingCourse",true);
            out.println(showPage(course,courseCategories));

        }
        else {
            courseController.saveCourse(course);
        }

    }

    public String showPage(Course course, List<CourseCategory> courseCategories){
        return "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "  <link rel=\"stylesheet\" type=\"text/css\" href=\"addCourseForm.css\">\n" +
                "</head>"+
                "<body>\n" +
                "\n" +
                "<h3><center>Add Course</center></h3>\n" +
                "\n" +
                "<div>\n" +
                "<form action=\"addCourse\" method=\"POST\">\n" +
                "\n"+
                createTextBox("courseName", course.getName(),"Course Name")+
                createTextBox("courseCode", course.getCode(),"Course Code")+
                createTextArea("syllabus", course.getSyllabus(), "Syllabus")+
                courseCategoryDropDown(course, courseCategories)+
                createCheckBox(course.isWorkshopEligibility(),"Workshop Eligibility","research_training_eligibility" )+
                createCheckBox(course.isInplantTrainingEligibility(), "Inplant Training Eligibility", "inplant_training_eligibility")+
                createCheckBox(course.isCorporateTrainingEligibility(), "Corporate Training Eligibility", "corporate_training_eligibility")+
                createCheckBox(course.isResearchTrainingEligibility(), "research_training_eligibility", "Research Training Eligibility" )+
                " <br>\n" +
                " <br>\n" +
                "<input type=\"submit\" value=\"Save\" >\n" +
                "\n" +
                " </form>\n" +
                "</div>\n" +
                "\n" +
                "</body>\n" +
                "</html>\n";

    }


    private String courseCategoryDropDown(Course course, List<CourseCategory> courseCategories) {
        String dropDown =  "<span style=\"font-size:18px;\">Training Course</span>\n" +
                          "<br>\n"+"<select name=\"cc\">\n";
        for (CourseCategory courseCategory : courseCategories){
            if(course.getCourseCategory().getId() == courseCategory.getId() ) {
                dropDown += "<option value=\"" + courseCategory.getId() + "\" selected>" + courseCategory.getName()
                        + "</option> ";
            }
            else {
                dropDown += "<option value=\"" + courseCategory.getId() + "\">" + courseCategory.getName() + "</option>";
            }
        }
        return dropDown+ "</select>\n";
    }

    private String createCheckBox(boolean eligibility, String caption, String checkBoxName){
        if(eligibility) {
            return "<input type=\"checkbox\" name=\""+checkBoxName+"\" value=\"TRUE\" checked>"+caption+"<br>\n";
        }
        else {
            return  "<input type=\"checkbox\" name=\""+checkBoxName+"\" value=\"TRUE\">"+caption+"<br>\n";
        }
    }

    private String createTextBox(String textBoxName, String textBoxValue, String placeHolder){
        return "<input type=\"text\" name=\""+textBoxName+"\" value=\""+textBoxValue+"\" placeholder=\""+placeHolder+"\">"
                + "<br>\n";
    }

    private String createTextArea(String textAreaName, String textAreaValue, String placeHolder){
        return "<span style=\"font-size:18px;\">"+placeHolder+"</span>\n" +
                "<br>\n" +
                "<textarea name=\""+textAreaName+"\" rows=\"10\" cols=\"30\" placeholder=\""+placeHolder+"\" >\n" +
                textAreaValue+"\n"+
                "</textarea>"+ "<br>\n";
    }
}

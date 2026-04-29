package com.gym.servlet;

import java.io.IOException;
import java.util.Vector;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.gym.dao.UserDAO;
import com.gym.model.AdminUserRow;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/ExportUsers")
public class ExportUsers extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        String filter = request.getParameter("filter");

        if (filter == null || filter.isBlank()) {
            filter = "all";
        }

        UserDAO dao = new UserDAO();
        Vector<AdminUserRow> list = dao.fetch(filter); // ✅ FILTER USED

        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet("Users");

        int rowNum = 0;

        // HEADER
        Row header = sheet.createRow(rowNum++);
        header.createCell(0).setCellValue("Username");
        header.createCell(1).setCellValue("Email");
        header.createCell(2).setCellValue("Phone");
        header.createCell(3).setCellValue("Plan");
        header.createCell(4).setCellValue("Start Date");
        header.createCell(5).setCellValue("End Date");
        header.createCell(6).setCellValue("Status");
        header.createCell(7).setCellValue("Days Left");

        // DATA
        for (AdminUserRow r : list) {

            Row row = sheet.createRow(rowNum++);

            row.createCell(0).setCellValue(r.getUsername());
            row.createCell(1).setCellValue(r.getEmail());
            row.createCell(2).setCellValue(r.getPhone());
            row.createCell(3).setCellValue(
                    r.getPlanName() != null ? r.getPlanName() : "NO PLAN"
            );
            row.createCell(4).setCellValue(
                    r.getStartDate() != null ? r.getStartDate() : "-"
            );
            row.createCell(5).setCellValue(
                    r.getEndDate() != null ? r.getEndDate() : "-"
            );
            row.createCell(6).setCellValue(r.getMembershipStatus());
            row.createCell(7).setCellValue(r.getDaysLeft());
        }

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=users.xlsx");

        wb.write(response.getOutputStream());
        wb.close();
    }
}
package com.gym.servlet;

import java.io.IOException;
import java.util.Vector;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.gym.dao.PaymentsDAO;
import com.gym.model.Payments;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/ExportPayments")
public class ExportPayments extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        PaymentsDAO dao = new PaymentsDAO();
        Vector<Payments> list = dao.getallpayments();

        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet("Payments");

        int rowNum = 0;

        // 🔥 HEADER
        Row header = sheet.createRow(rowNum++);
        header.createCell(0).setCellValue("User");
        header.createCell(1).setCellValue("Amount");
        header.createCell(2).setCellValue("Status");
        header.createCell(3).setCellValue("Date");
        header.createCell(4).setCellValue("Payment ID");
        header.createCell(5).setCellValue("Order ID");

        // 🔥 DATA
        for (Payments p : list) {
            Row row = sheet.createRow(rowNum++);

            row.createCell(0).setCellValue(
                p.getEmail() != null ? p.getEmail() : "-"
            );
            row.createCell(1).setCellValue(p.getAmount());
            row.createCell(2).setCellValue(p.getStatus());
            row.createCell(3).setCellValue(p.getCreatedAt());
            row.createCell(4).setCellValue(p.getPaymentId());
            row.createCell(5).setCellValue(p.getOrderId());
        }

        // 🔥 DOWNLOAD
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=payments.xlsx");

        wb.write(response.getOutputStream());
        wb.close();
    }
}
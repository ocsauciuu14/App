package com.example.myapplication;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText EditId, EditTen, EditTuoi, EditGpa;
    private Button ThemStudent, XoaStudent;
    private TableLayout TableStudent;

    // Danh sách sinh viên
    private ArrayList<Student> listStudent = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Ánh xạ View từ XML
        EditId = findViewById(R.id.EditId);
        EditTen = findViewById(R.id.EditTen);
        EditTuoi = findViewById(R.id.EditTuoi);
        EditGpa = findViewById(R.id.EditGpa);
        ThemStudent = findViewById(R.id.ThemStudent);
        XoaStudent = findViewById(R.id.XoaStudent);
        TableStudent = findViewById(R.id.TableStudent);

        // Sự kiện thêm sinh viên
        ThemStudent.setOnClickListener(v -> themSinhVien());

        // Sự kiện xóa sinh viên theo mã
        XoaStudent.setOnClickListener(v -> xoaSinhVien());
    }
    private void themSinhVien() {
        // 1. Lấy dữ liệu từ giao diện
        String id = EditId.getText().toString().trim();
        String name = EditTen.getText().toString().trim();
        String ageStr = EditTuoi.getText().toString().trim();
        String gpaStr = EditGpa.getText().toString().trim();
        // 2. Kiểm tra dữ liệu nhập vào
        if (id.isEmpty() || name.isEmpty() || ageStr.isEmpty() || gpaStr.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
            return;
        }
        for (Student s : listStudent) {
            if (s.GetId().equalsIgnoreCase(id)) {
                Toast.makeText(this, "Mã sinh viên đã tồn tại!", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        try {
            Integer age = Integer.parseInt(ageStr);
            Float gpa = Float.parseFloat(gpaStr);

            if (gpa < 0 || gpa > 10) {
                Toast.makeText(this, "GPA phải nằm trong thang điểm 0 - 10!", Toast.LENGTH_SHORT).show();
                return;
            }
            // 3. Tạo đối tượng Student
            Student newStudent = new Student(id, name, age, gpa);

            // 4. Thêm đối tượng vào ArrayList<Student>
            listStudent.add(newStudent);

            // 5. Hiển thị sinh viên lên TableLayout (Mã, Họ tên, Tuổi, GPA, Xếp loại)
            hienThiLenBang(newStudent);

            Toast.makeText(this, "Thêm sinh viên thành công!", Toast.LENGTH_SHORT).show();
            xoaTrangForm();

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Tuổi hoặc GPA phải là chữ số hợp lệ!", Toast.LENGTH_SHORT).show();
        }
    }
    private void hienThiLenBang(Student s) {
        TableRow row = new TableRow(this);
        row.setPadding(0, 12, 0, 12);
        row.setTag(s.GetId()); // Gắn tag để quản lý xóa dòng

        row.addView(taoOVanBan(s.GetId()));
        row.addView(taoOVanBan(s.GetName()));
        row.addView(taoOVanBan(String.valueOf(s.GetAge())));
        row.addView(taoOVanBan(String.valueOf(s.GetGpa())));
        row.addView(taoOVanBan(s.getRank())); // Lấy xếp loại từ hàm getRank()

        TableStudent.addView(row);
    }
    private void xoaSinhVien() {
        String idXoa = EditId.getText().toString().trim();

        if (idXoa.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập Mã SV cần xóa vào ô Masv!", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean timThay = false;
        for (int i = 0; i < listStudent.size(); i++) {
            if (listStudent.get(i).GetId().equalsIgnoreCase(idXoa)) {
                listStudent.remove(i);
                timThay = true;
                break;
            }
        }
        if (timThay) {
            for (int i = 1; i < TableStudent.getChildCount(); i++) {
                TableRow row = (TableRow) TableStudent.getChildAt(i);
                if (row.getTag() != null && row.getTag().toString().equalsIgnoreCase(idXoa)) {
                    TableStudent.removeViewAt(i);
                    break;
                }
            }
            Toast.makeText(this, "Đã xóa sinh viên: " + idXoa, Toast.LENGTH_SHORT).show();
            xoaTrangForm();
        } else {
            Toast.makeText(this, "Không tìm thấy sinh viên có mã: " + idXoa, Toast.LENGTH_SHORT).show();
        }
    }
    private TextView taoOVanBan(String text) {
        TextView tv = new TextView(this);
        tv.setText(text);
        tv.setTextColor(Color.DKGRAY);
        tv.setTextSize(14);
        return tv;
    }
    private void xoaTrangForm() {
        EditId.setText("");
        EditTen.setText("");
        EditTuoi.setText("");
        EditGpa.setText("");
        EditId.requestFocus();
    }
}
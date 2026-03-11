/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lesson2;

/**
 *
 * @author ADMIN
 */
public class DonHang {
    private int id;
    private String hoTenKH;
    private TrangThai trangThai; 
    public DonHang(int id, String hoTenKH, TrangThai trangThai){
        this.id = id;
        this.hoTenKH = hoTenKH;
        this.trangThai = trangThai;
    }
    public TrangThai getTrangThai(){
        return this.trangThai;
    }
     public void setTrangThai(TrangThai trangThai){
        this.trangThai =  trangThai;
    }
     //Trong câu lệnh switch-case
     public void thoiGianTrangThai(){
         switch (this.getTrangThai()) {
             case NHAN_DON:
                 System.out.println("Thoi gian la: "+ TrangThai.GIAO_HANG.thoiGian);
                 break;
                 case THUC_HIEN:
                 System.out.println("Thoi gian la: "+ TrangThai.THUC_HIEN.thoiGian);
                 break;
                 case GIAO_HANG:
                 System.out.println("Thoi gian la: "+ TrangThai.GIAO_HANG.thoiGian);
                 break;
             default:
                 System.out.println("Trang thai k xac dinh");
         }
     }
}

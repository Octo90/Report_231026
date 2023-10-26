package Exam_231026;

import java.sql.*;
import java.util.Scanner;

class PhoneB{
    String pName, pNum, addP;

    public String getpName() {return pName;}
    public String getpNum() {return pNum;}
    public String getaddP() {return addP;}

    public void setpName(String pName) {this.pName = pName;}
    public void setpNum(String pNum) {this.pNum = pNum;}
    public void setaddP(String addP) {this.addP = addP;}
}


class SQLP {
    private static Connection conn;
    private static PreparedStatement pstmt;

    SQLP() throws SQLException {
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/exam", "root", "1234");
    }

    void PhoneBInsert(PhoneB pb) {
        try {
            pstmt = conn.prepareStatement(" insert into phone values (?,?,?);");
            pstmt.setString(1, pb.getpName());
            pstmt.setString(2, pb.getpNum());
            pstmt.setString(3, pb.getaddP());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void pChoice(String stp) throws SQLException {
        String sql = "select * from phone where name = '"+stp+"'";
        pstmt = conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            System.out.println("=====================================================");
            System.out.print("이름 : " + rs.getString("name") + ", ");
            System.out.print("전화번호 : " + rs.getString("phoneNumber") + ", ");
            System.out.println("주소 : " + rs.getString("address"));
            System.out.print("=====================================================");
            System.out.println();
        } else {
            System.out.println("전화번호부에 없습니다.");
        }
    }

    void pAll() throws SQLException {
        String sql = "select * from phone;";
        pstmt = conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();

        System.out.println("=====================================================");
        while (rs.next()) {
            System.out.print("이름 : " + rs.getString("name") + ", ");
            System.out.print("전화번호 : " + rs.getString("phoneNumber") + ", ");
            System.out.print("주소 : " + rs.getString("address"));
            System.out.println();
        }System.out.println("=====================================================");
    }

    void pDelete(String stp) throws SQLException {
        String sql = "delete from phone where name = '"+stp+"'";
        pstmt = conn.prepareStatement(sql);
        if (pstmt.executeUpdate() == 1) {
            System.out.println("=====================================================");
            System.out.println(stp + "이(가) 삭제되었습니다.");
            System.out.println("=====================================================");
        } else {
            System.out.println("=====================================================");
            System.out.println("전화번호부에 없습니다..");
            System.out.println("=====================================================");
        }
    }
}

class InputClass {
    PhoneB valueReturn() {
        PhoneB pb = new PhoneB();
        Scanner sc1 = new Scanner(System.in);

        System.out.print("이름 : ");
        pb.setpName(sc1.nextLine());
        System.out.print("전화번호 : ");
        pb.setpNum(sc1.nextLine());
        System.out.print("주소 : ");
        pb.setaddP(sc1.nextLine());

        return pb;
    }
}
public class JavaTest_231026 {
    public static void main(String[] args) throws SQLException {
        Scanner sc = new Scanner(System.in);

        SQLP sqlp = new SQLP();
        InputClass ic = new InputClass();
        while (true) {
            System.out.print("1. 입력  2. 검색  3. 삭제  4. 출력  5.종료\n -> ");
            int num = sc.nextInt();
            sc.nextLine();
            if (num == 1) {
                sqlp.PhoneBInsert(ic.valueReturn());
            } else if (num == 2) {
                System.out.print("이름 : ");
                String stp = sc.next();
                sqlp.pChoice(stp);
            } else if (num == 3) {
                System.out.print("이름 : ");
                String stp = sc.next();
                sqlp.pDelete(stp);
            } else if (num == 4) {
                sqlp.pAll();
            } else if (num == 5) {
                System.out.println("전화번호부를 종료합니다.");
                break;
            } else {
                System.out.println("잘못된 입력입니다.");
            }
        }
    }
}

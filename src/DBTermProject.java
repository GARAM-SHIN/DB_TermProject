import java.sql.*;
import java.util.Scanner;

public class DBTermProject {

    // MySQL Connection 정보
    static final String DB_URL = "jdbc:mysql://192.168.56.101:4567/DBTermProject";
    static final String USER = "garam-shin";
    static final String PASSWORD = "1234";

    // MySQL 연결 객체
    private static Connection connection;

    // 메뉴 옵션
    private static final int CONNECTION = 1;
    private static final int INSERT = 2;
    private static final int FIND = 3;
    private static final int UPDATE = 4;
    private static final int DELETE = 5;
    private static final int QUIT = 99;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // 메뉴를 보여주고 사용자가 선택하도록 유도
        while (true) {
            printMenu();
            int choice = scanner.nextInt();

            switch (choice) {
                case CONNECTION:
                    connectToDatabase();
                    break;
                case INSERT:
                    insertData(scanner);
                    break;
                case FIND:
                    findData(scanner);
                    break;
                case UPDATE:
                    updateData(scanner);
                    break;
                case DELETE:
                    deleteData(scanner);
                    break;
                case QUIT:
                    quitApplication();
                    return;
                default:
                    System.out.println("잘못된 선택입니다. 다시 시도해 주세요.");
            }
        }
    }

    // 메뉴 출력
    private static void printMenu() {
        System.out.println("\n********** Menu **********");
        System.out.println("1. Connect to Database");
        System.out.println("2. Insert Data");
        System.out.println("3. Find Data");
        System.out.println("4. Update Data");
        System.out.println("5. Delete Data");
        System.out.println("99. Quit");
        System.out.print("Choose an option: ");
    }

    // MySQL에 연결
    private static void connectToDatabase() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
                System.out.println("데이터베이스에 연결되었습니다.");
            } else {
                System.out.println("이미 데이터베이스에 연결되어 있습니다.");
            }
        } catch (SQLException e) {
            System.out.println("연결 실패: " + e.getMessage());
        }
    }

    // 데이터 삽입
    private static void insertData(Scanner scanner) {
        try {
            System.out.print("Insert into which table? (예: Club): ");
            String table = scanner.next();
            System.out.print("Enter the data to insert: ");
            String data = scanner.next();

            String query = "INSERT INTO " + table + " VALUES (" + data + ")";
            Statement stmt = connection.createStatement();
            int rowsAffected = stmt.executeUpdate(query);
            System.out.println("데이터 삽입 완료: " + rowsAffected + " 행 삽입되었습니다.");
        } catch (SQLException e) {
            System.out.println("삽입 실패: " + e.getMessage());
        }
    }

    // 데이터 조회
    private static void findData(Scanner scanner) {
        try {
            System.out.print("Enter the table to search in: ");
            String table = scanner.next();
            System.out.print("Enter the condition (e.g., id=1): ");
            String condition = scanner.next();

            String query = "SELECT * FROM " + table + " WHERE " + condition;
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                // 예시: Club 테이블에 컬럼이 Cname, Room이라면 아래처럼 출력
                System.out.println("Cname: " + rs.getString("Cname") + ", Room: " + rs.getString("Room"));
            }
        } catch (SQLException e) {
            System.out.println("조회 실패: " + e.getMessage());
        }
    }

    // 데이터 업데이트
    private static void updateData(Scanner scanner) {
        try {
            System.out.print("Enter the table to update: ");
            String table = scanner.next();
            System.out.print("Enter the column to update (e.g., Room='NewRoom'): ");
            String updateColumn = scanner.next();
            System.out.print("Enter the condition to apply the update (e.g., Cname='MyClub'): ");
            String condition = scanner.next();

            String query = "UPDATE " + table + " SET " + updateColumn + " WHERE " + condition;
            Statement stmt = connection.createStatement();
            int rowsAffected = stmt.executeUpdate(query);
            System.out.println("데이터 업데이트 완료: " + rowsAffected + " 행이 업데이트되었습니다.");
        } catch (SQLException e) {
            System.out.println("업데이트 실패: " + e.getMessage());
        }
    }

    // 데이터 삭제
    private static void deleteData(Scanner scanner) {
        try {
            System.out.print("Enter the table to delete from: ");
            String table = scanner.next();
            System.out.print("Enter the condition for deletion (e.g., Cname='MyClub'): ");
            String condition = scanner.next();

            String query = "DELETE FROM " + table + " WHERE " + condition;
            Statement stmt = connection.createStatement();
            int rowsAffected = stmt.executeUpdate(query);
            System.out.println("데이터 삭제 완료: " + rowsAffected + " 행이 삭제되었습니다.");
        } catch (SQLException e) {
            System.out.println("삭제 실패: " + e.getMessage());
        }
    }

    // 프로그램 종료
    private static void quitApplication() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("데이터베이스 연결을 종료합니다.");
            }
        } catch (SQLException e) {
            System.out.println("연결 종료 실패: " + e.getMessage());
        }
        System.out.println("프로그램을 종료합니다.");
    }
}
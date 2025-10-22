SheikPlastic Backend (SQL Server configured)
=============================================

This project is configured to use the SheikPlastic SQL Server instance:

- JDBC URL: jdbc:sqlserver://CENTRAL\SQLEXPRESS;databaseName=sheikplastic;encrypt=false;trustServerCertificate=true
- Username: sa
- Password: (from global.asa) ledemi54
- Jwt secret set to a strong key (in application.yml)
- Token expiration: 2 hours

How to run:
1. Install JDK 17 and Maven 3.9+
2. Adjust application.yml if your SQL Server instance uses different host/credentials
3. Build: mvn clean package
4. Run: java -jar target/sheikplastic-backend-1.0.0.jar
5. Swagger UI: http://localhost:8080/swagger-ui.html

package com.sheikplastic;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;

@Component
public class TesteConexao implements CommandLineRunner {

    private final DataSource dataSource;

    public TesteConexao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void run(String... args) throws Exception {
        try (Connection conn = dataSource.getConnection()) {
            System.out.println("✅ Conexão estabelecida com sucesso: " + conn.getMetaData().getURL());
        } catch (Exception e) {
            System.err.println("❌ Erro ao conectar ao banco: " + e.getMessage());
        }
    }
}

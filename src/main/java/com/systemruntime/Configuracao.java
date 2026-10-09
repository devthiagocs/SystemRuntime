package com.systemruntime;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Configuracao {
    public static final File PASTA = new File(System.getenv("ProgramData"), "SystemRuntime");
    public static final File ARQUIVO = new File(PASTA, "horario.txt");
    public static final File LOG = new File(PASTA, "log.txt");
    public static final DateTimeFormatter LOG_FORMATADO = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    public String configurarHorario(File arquivo) {
        System.out.println(" =========== BLOQUEIO DOS COMPUTADORES =========== ");
        Scanner leitura = new Scanner(System.in);
        System.out.println("Digite o horário desejado para que seja realizado o bloqueio (Exemplo: \"17:15\"): ");
        String horarioConfiguracao = leitura.nextLine();

        try {
            while (!horarioValido(horarioConfiguracao)) {
                System.out.println("Horário inválido! Digite novamente:");
                horarioConfiguracao = leitura.nextLine();
            }

            try {
                PASTA.mkdirs();
                FileWriter file = new FileWriter(ARQUIVO);
                file.write(horarioConfiguracao);
                file.close();
            } catch (IOException e) {
                Configuracao.log("Erro ao configurar horário" + e);
            }
        } catch (Exception e) {
            Configuracao.log("Erro: " + e);
        }
        leitura.close();
        return horarioConfiguracao;
    }

    public static boolean horarioValido(String horario) {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("HH:mm");
        try {
            LocalTime.parse(horario, formato);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public static void log(String mensagem) {
        try {
            PASTA.mkdirs();
            Files.writeString(LOG.toPath(),
                    LocalDateTime.now().format(LOG_FORMATADO) + " " + mensagem + System.lineSeparator(),
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (Exception e) {

        }
    }
}

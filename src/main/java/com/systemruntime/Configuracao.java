package com.systemruntime;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Configuracao {
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
                FileWriter file = new FileWriter("horario.txt");
                file.write(horarioConfiguracao);
                file.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
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
}

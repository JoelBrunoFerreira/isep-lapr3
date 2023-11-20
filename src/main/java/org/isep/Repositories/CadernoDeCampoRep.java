package org.isep.Repositories;



import org.isep.Domain.OperacaoCC;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class CadernoDeCampoRep {
    private static final String FILE_PATH = "cCampo.ser";
    private List<OperacaoCC> operacoesCC = recuperarCaderonoDeCampoInfo();

    public CadernoDeCampoRep() {
        if (operacoesCC.isEmpty()) {
            operacoesCC = new ArrayList<>();
            arquivarCaderonoDeCampoInfo();
        }
    }

    public List<OperacaoCC> getOperacoesCC() {
        return operacoesCC;
    }

    public void registarOperacao(OperacaoCC op) {
        if (!operacoesCC.contains(op)) {
            operacoesCC.add(op);
        }
    }

    private void arquivarCaderonoDeCampoInfo() {
        try {
            FileOutputStream fileOut = new FileOutputStream(FILE_PATH);
            ObjectOutputStream outputStream = new ObjectOutputStream(fileOut);
            outputStream.writeObject(operacoesCC);
            outputStream.close();
            fileOut.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<OperacaoCC> recuperarCaderonoDeCampoInfo() {
        List<OperacaoCC> result = new ArrayList<>();
        try {
            FileInputStream fileInputStream = new FileInputStream(FILE_PATH);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            result = (List<OperacaoCC>) objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return result;
    }
}

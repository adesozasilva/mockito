package br.com.adesozasilva.infra.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import br.com.adesozasilva.domain.Filme;

public class BancoDeDados {
    
    public static void gravaNoBanco(List<Filme> filmes, String nomeArq) {
      File arq = new File(nomeArq);
      try {
        arq.delete();
        arq.createNewFile();
    
        ObjectOutputStream objOutput = new ObjectOutputStream(new FileOutputStream(arq));
        objOutput.writeObject(filmes);
        objOutput.close();
    
      } catch(IOException erro) {
          System.out.printf("Erro: %s", erro.getMessage());
      }
    }
    
	public static List<Filme> lerDoBanco(String nomeArq) {
      List<Filme> lista = new ArrayList<Filme>();
      try {
        File arq = new File(nomeArq);
        if (arq.exists()) {
           ObjectInputStream objInput = new ObjectInputStream(new FileInputStream(arq));
           lista = (ArrayList<Filme>)objInput.readObject();
           objInput.close();
        }
      } catch(IOException erro1) {
          System.out.printf("Erro: %s", erro1.getMessage());
      } catch(ClassNotFoundException erro2) {
          System.out.printf("Erro: %s", erro2.getMessage());
      }
    
      return(lista);
    }


    public static void deleteBancoDeDados(String nomeArq) {
        File arq = new File(nomeArq);
        arq.delete();
    }
    
  }
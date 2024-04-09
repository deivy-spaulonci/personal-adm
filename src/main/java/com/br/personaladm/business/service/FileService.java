package com.br.personaladm.business.service;

import com.br.personaladm.domain.model.Conta;
import com.br.personaladm.domain.model.parametro.ChavePasta;
import com.br.personaladm.domain.model.parametro.Parametro;
import com.br.personaladm.domain.model.parametro.SulFixoTipoArquivo;
import com.br.personaladm.domain.repository.ParametroRepository;
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.model.enums.CompressionLevel;
import net.lingala.zip4j.model.enums.EncryptionMethod;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Base64;

@Service
public class FileService {

    @Autowired
    private ParametroRepository parametroRepository;


    /**
     * excluir aquivos pelo nome e chave/pasta
     * @param nome
     * @param chavePasta
     * @return
     */
    public Boolean excluirArquivo(String nome, ChavePasta chavePasta){
        try {
            Parametro par = parametroRepository.findParametroByChave(chavePasta.name());
            Path arquivo = Paths.get(par.getValor()+nome);
            return Files.deleteIfExists(arquivo);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return false;
    }
    /**
     * pego o arquivo de titulo/boleto de contas pelo nome
     * @param fileCode
     * @return
     */
    public Resource getArquivo(String nomeArquivoComExtensao, ChavePasta chavePasta){
        try {
            Parametro pastaFonte = parametroRepository.findParametroByChave(chavePasta.name());
            Parametro pastaTemp = parametroRepository.findParametroByChave(ChavePasta.PASTA_TMP.name());
            if(!new File(pastaTemp.getValor()).exists())
                new File(pastaTemp.getValor()).mkdir();
            descompactaArquivo(nomeArquivoComExtensao, pastaFonte.getValor(), pastaTemp.getValor());

            Path arquivo = Paths.get(pastaTemp.getValor()+"/"+nomeArquivoComExtensao);
            return new UrlResource(arquivo.toUri());
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * descompacta dentro da pasta armazeanda
     * @param fileName
     * @param path
     */
    public void descompactaArquivo(String nomeComExtencao,
                                   String path,
                                   String localExtracao){
        try{
            String nomeSemExtencao = FilenameUtils.getBaseName(nomeComExtencao);
            Parametro parametro = parametroRepository.findParametroByChave(ChavePasta.PW_ZIP.name());

            ZipFile zipFile = new ZipFile(path + nomeSemExtencao+".zip",
                    new String(Base64.getDecoder().decode(parametro.getValor())).toCharArray());
            zipFile.extractAll(localExtracao);
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }

    /**
     * Crio um arquivo temporário para o MultipartFile
     * Cria o arquivo ZIP e retiro a extencao do arquivo
     * Remove o arquivo temporário
     * @param multipartFile
     * @param chavePasta
     * @param novoNomeComExtensao
     */
    public void createFileCompacated(MultipartFile multipartFile,
                             ChavePasta chavePasta,
                             String novoNomeComExtensao){
        try{
            Parametro parametro = parametroRepository.findParametroByChave(chavePasta.name());
            if(!new File(parametro.getValor()).exists())
                throw new FileNotFoundException("Pasta não existe: " + parametro.getValor());

            File fileTMP = new File("/"+novoNomeComExtensao);
            multipartFile.transferTo(fileTMP);
            fileTMP.createNewFile();

            compactaArquivo(fileTMP,
                    parametro.getValor()+FilenameUtils.getBaseName(novoNomeComExtensao));

            Files.deleteIfExists(fileTMP.toPath());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * recebo o objeto do arquivo e o nome com caminho do arquivo sem extensao para compactar
     * @param file
     * @param nomeCaminhoAquivoSemExtencao
     */
    public void compactaArquivo(File file, String nomeCaminhoAquivoSemExtencao){
        try{
            ZipParameters zipParameters = new ZipParameters();
            zipParameters.setEncryptFiles(true);
            zipParameters.setCompressionLevel(CompressionLevel.HIGHER);
            zipParameters.setEncryptionMethod(EncryptionMethod.AES);

            Parametro parametro = parametroRepository.findParametroByChave(ChavePasta.PW_ZIP.name());

            ZipFile zipFile = new ZipFile(
                    nomeCaminhoAquivoSemExtencao+".zip",
                    new String(Base64.getDecoder().decode(parametro.getValor())).toCharArray()
            );
            zipFile.addFile(file, zipParameters);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}

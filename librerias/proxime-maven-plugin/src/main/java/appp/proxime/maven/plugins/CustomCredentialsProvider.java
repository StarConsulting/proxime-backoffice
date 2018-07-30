/*
 * Copyright (C) Proxime SpA - Todos los derechos reservados
 * Queda expresamente prohibida la copia o reproducción total o parcial de este archivo
 * sin el permiso expreso y por escrito de Proxime SpA.
 * La detección de un uso no autorizado puede acarrear el inicio de acciones legales.
 */
package appp.proxime.maven.plugins;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Michel M. <michel@febos.cl>
 */
class CustomCredentialsProvider implements AWSCredentialsProvider {

    public Properties props;
    public FebosCredentials credentials;

    public void load(File properties){
        FebosCredentials c=new FebosCredentials(properties);
        credentials=c;
        props=c.props;
    }
    @Override
    public AWSCredentials getCredentials() {
        return credentials;
    }

    @Override
    public void refresh() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public class FebosCredentials implements AWSCredentials {
        public Properties props;
        public FebosCredentials(File properties) {
            props = new Properties();
            try {
                props.load(new FileInputStream(properties));
            } catch (IOException ex) {
                Logger.getLogger(CustomCredentialsProvider.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        public Properties getProps(){
            return this.props;
        }
        
        @Override
        public String getAWSAccessKeyId() {
            return props.getProperty("key");
        }

        @Override
        public String getAWSSecretKey() {
            return props.getProperty("secret");
        }

    }

}

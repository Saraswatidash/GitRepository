package filetodownload;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.KeyStore;
import java.util.List;

import javax.net.SocketFactory;
import javax.net.ssl.SSLSocketFactory;

import org.mule.api.MuleEventContext;
import org.mule.api.lifecycle.Callable;

import com.github.sardine.DavResource;
import com.github.sardine.Sardine;
import com.github.sardine.SardineFactory;

public class files implements Callable{

		@Override
		public Object onCall(MuleEventContext eventContext) throws Exception {
			// TODO Auto-generated method stub

		String keyStoreFilename = "D:/DownloadedFiles/TF.keystore";
			File keystoreFile = new File(keyStoreFilename);
			FileInputStream fis = new FileInputStream(keystoreFile);        
			KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType()); // JKS
			keyStore.load(fis, null);
			final SocketFactory socketFactory= SSLSocketFactory.getDefault();
	
			/*//ProxySelector prxy = new  proxy
			
			Sardine sardine = new SardineImpl("crc", "tarantallegra") {
		

			       @Override
			        protected ConnectionSocketFactory createDefaultSecureSocketFactory() {
			        	// TODO Auto-generated method stub
			        	return 
			        }  
			};*/
			
			
        	Sardine sr=SardineFactory.begin("crc", "tarantallegra");
			//Sardine sardine1 = SardineFactory.begin();
		
			
			List<DavResource> resources = sr.list("https://webdav.atypon.com/crc/reports/counter");
			
			String url = "https://webdav.atypon.com";
			
	        for (DavResource res : resources)
			{
				
				BufferedInputStream in = null;
				FileOutputStream fout = null;
				System.out.println(":::"+res);
				if (!res.isDirectory())
				{
				String NewFile1 = res.toString().substring(res.toString().lastIndexOf("/"), res.toString().length());
				
				try {
					 in = new BufferedInputStream(sr.get(url+res));
					 fout = new FileOutputStream("D:/NewFile1"+NewFile1);

					byte data[] = new byte[1024];
					 int count;
					 while ((count = in.read(data, 0, 1024)) != -1) {
					 fout.write(data, 0, count);
					 }
					 } finally {
					 if (in != null)
					 in.close();
					 if (fout != null)
					 fout.close();
					 }
				}
			      
            	
		}
				
				return resources;
				  }

				
		}
		



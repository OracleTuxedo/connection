
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import weblogic.wtc.gwt.TuxedoConnection;
import weblogic.wtc.gwt.TuxedoConnectionFactory;
import weblogic.wtc.jatmi.Reply;
import weblogic.wtc.jatmi.TPException;
import weblogic.wtc.jatmi.TPReplyException;
import weblogic.wtc.jatmi.TypedCArray;

import java.util.Arrays;
import java.util.Hashtable;
import java.util.Properties;

public class Main {
    public static void main(String[] args) throws Exception {
//        Context ctx;
        //testt
        TuxedoConnectionFactory tcf;
        TuxedoConnection myTux = null;
        TypedCArray myData;
        Reply rtnCd;
        byte[] ret;

        System.out.println("testing");
        TypedCArray myDataBack;
        Hashtable<String, String> env = new Hashtable<>();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
        env.put(Context.PROVIDER_URL, "t3://172.16.54.23:16998"); // Change this to your WebLogic server URL

        try {
            //make context
            System.out.println("testing 1");
            InitialContext ctx = new InitialContext(env);
            System.out.println("1");
            tcf = (TuxedoConnectionFactory) ctx.lookup("tuxedo.services.TuxedoConnection");
            myTux = tcf.getTuxedoConnection();
            System.out.println("2");
            byte[] inputData =
                    "Test"
                    .getBytes();

            System.out.println("3");
            myData = new TypedCArray();
            myData.carray = inputData;
            myData.setSendSize(inputData.length);

            System.out.println("4");
            rtnCd = myTux.tpcall("SLCFPROXY", myData, 0);
            myDataBack = (TypedCArray) rtnCd.getReplyBuffer();
            System.out.println("5");
            ret = myDataBack.carray;
        } catch (NamingException var15) {
            System.out.println("Could not get TuxedoConnectionFactory : NamingException:" + var15.getMessage());
            throw var15;
        } catch (TPReplyException var16) {
            System.out.println("tpcall threw TPReplyExcption");

            throw var16;
        } catch (TPException var17) {
            System.out.println("tpcall threw TPException");

            throw var17;
        } catch (Exception var18) {
            System.out.println("tpcall threw exception: ");
            throw var18;
        } finally {
            if (myTux != null) {
                myTux.tpterm();
            }
        }
        System.out.println(Arrays.toString(ret));
    }
}
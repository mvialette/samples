package com.vialette.maxime.test.wicket_sample;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Statement;

import org.dbunit.DBTestCase;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;

public class SampleBDDTest extends DBTestCase {

    public SampleBDDTest(String name) {
        super(name);

        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS,
 "org.apache.derby.jdbc.ClientDriver");

        // System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS,
        // "org.apache.derby.jdbc.EmbeddedDriver");

        // System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL, "jdbc:derby:mabase;create=true");
        // System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME, "");
        // System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD, "");
        // System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_SCHEMA, "APP");

        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL,
                "jdbc:derby://localhost:1527/D:/Max/java/utilisateurDB");
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME, "user");
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD, "password");
    }

    @Override
    protected DatabaseOperation getSetUpOperation() throws Exception {
        return DatabaseOperation.CLEAN_INSERT;
    }

    @Override
    protected void setUp() throws Exception {
        IDatabaseConnection databaseConnection = getDatabaseTester().getConnection();
        executeDDL(databaseConnection, "create-database.ddl");

        // Statement st = databaseConnection.getConnection().createStatement();
        // st.execute("insert into adresse values('bvd des etats unis', 75000, 'Paris')");

        super.setUp();
    }

    @Override
    protected DatabaseOperation getTearDownOperation() throws Exception {
        return DatabaseOperation.NONE;
    }

    private void executeDDL(IDatabaseConnection databaseConnection, String filename) {
        try {
            Statement st = databaseConnection.getConnection().createStatement();

            String contenuDuFichier = loadDDL(filename);
            String[] lesInstructionsSQL = contenuDuFichier.split(";");
            for (String uneInstructionSQL : lesInstructionsSQL) {
                if (!"\r\n".equals(uneInstructionSQL)) {
                    st.execute(uneInstructionSQL);
                }
            }

            st.execute("insert into adresse(rue, codepostal, ville) values('bvd des etats unis', 75000, 'Paris')");

        } catch (Exception e) {
            fail(e.getMessage());
        } finally {
            try {
                getDatabaseTester().closeConnection(databaseConnection);
            } catch (Exception e) {
                // ignore
            }
        }
    }

    private String loadDDL(String filename) throws IOException {
        InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(filename);
        BufferedReader r = new BufferedReader(new InputStreamReader(in));
        StringBuffer buffer = new StringBuffer();
        String line = null;
        String EOL = System.getProperty("line.separator");
        while ((line = r.readLine()) != null) {
            if (!line.isEmpty() && !line.startsWith("#")) {
                buffer.append(line + EOL);
            }
        }
        in.close();
        return buffer.toString();
    }

    @Override
    protected IDataSet getDataSet() throws Exception {
        FileInputStream fis = new FileInputStream(
                "D:/Max/java/Workspace_current/wicket-sample/src/test/resources/DataSet/utilisateur.xml");
        // FileInputStream fis = new FileInputStream("utilisateur.xml");
        return new FlatXmlDataSetBuilder().build(fis);
    }

    public void testInsertData() {
        assertEquals(true, true);
    }
}

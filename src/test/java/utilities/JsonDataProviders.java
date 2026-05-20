package utilities;

import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.FileNotFoundException;
import java.util.List;

import org.testng.annotations.DataProvider;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class JsonDataProviders {

    private static final Gson gson = new Gson();

    @DataProvider(name = "RegisterDataHybrid")
    public Object[][] getRegisterDataHybrid() throws Exception {
        InputStream is = getClass().getClassLoader().getResourceAsStream("testdata/RegisterData.json");
        if (is == null) throw new FileNotFoundException("RegisterData.json not found in classpath under testdata/");
        InputStreamReader reader = new InputStreamReader(is);

        List<RegisterData> users = gson.fromJson(reader, new TypeToken<List<RegisterData>>(){}.getType());
        Object[][] data = new Object[users.size()][5];
        for (int i = 0; i < users.size(); i++) {
            RegisterData u = users.get(i);

            // Generate unique email by appending timestamp
            String baseEmail = u.getEmail();
            String uniqueEmail = baseEmail.replace("@", "+" + System.currentTimeMillis() + "@");

            data[i][0] = u.getFirstname();
            data[i][1] = u.getLastname();
            data[i][2] = uniqueEmail;   // use unique email
            data[i][3] = u.getPassword();
            data[i][4] = u.getMobile();
        }
        return data;
    }


    // Login DataProvider
    @DataProvider(name="LoginData")
    public Object[][] getLoginData() throws Exception {
        FileReader reader = new FileReader("./src/test/resources/testdata/LoginData.json");
        List<LoginData> arr = gson.fromJson(reader, new TypeToken<List<LoginData>>(){}.getType());

        Object[][] data = new Object[arr.size()][3];
        for (int i = 0; i < arr.size(); i++) {
            LoginData obj = arr.get(i);
            data[i][0] = obj.getUsername();
            data[i][1] = obj.getPassword();
            data[i][2] = obj.getExp();
        }
        return data;
    }

    // Search DataProvider
    @DataProvider(name = "SearchData")
    public Object[][] getSearchData() throws Exception {
        InputStream is = getClass().getClassLoader().getResourceAsStream("testdata/searchdata.json");
        if (is == null) throw new FileNotFoundException("searchdata.json not found in classpath under testdata/");
        InputStreamReader reader = new InputStreamReader(is);

        List<SearchData> products = gson.fromJson(reader, new TypeToken<List<SearchData>>(){}.getType());

        Object[][] data = new Object[products.size()][2];
        for (int i = 0; i < products.size(); i++) {
            data[i][0] = products.get(i).getProductName();
            data[i][1] = products.get(i).getCategory();
        }
        return data;
    }



    @DataProvider(name = "CartData")
    public Object[][] getCartData() throws Exception {
        // Load JSON from classpath (src/test/resources/testdata/cartdata.json)
        InputStream is = getClass().getClassLoader().getResourceAsStream("testdata/cartdata.json");
        if (is == null) {
            throw new FileNotFoundException("cartdata.json not found in classpath under testdata/");
        }
        InputStreamReader reader = new InputStreamReader(is);

        // Create Gson instance
        Gson gson = new Gson();

        // Deserialize JSON into List<CartData>
        List<CartData> products = gson.fromJson(reader, new TypeToken<List<CartData>>() {}.getType());

        // Build Object[][] for TestNG
        Object[][] data = new Object[products.size()][1];
        for (int i = 0; i < products.size(); i++) {
            data[i][0] = products.get(i).getProductName();
        }
        return data;
    }
}
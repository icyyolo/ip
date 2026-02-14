package zhongli.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import zhongli.product.Product;
import zhongli.product.ProductList;
import zhongli.ui.Ui;
import zhongli.zhongliexception.ZhongliException;

/**
 * Represents a storage class to handle product objects.
 * It can read from file, and write to file.
 *
 */
public class ProductStorage extends Storage {

    public ProductStorage(String filePath) {
        super(filePath);
    }

    private ArrayList<Product> getProductsFromFile(Ui ui) throws FileNotFoundException {
        File file = super.readFile(filePath, ui);
        Scanner s = new Scanner(file);

        ArrayList<Product> products = new ArrayList<>();
        int lineNum = 0;

        while (s.hasNext()) {
            lineNum++;
            String curr = s.nextLine();

            try {
                Product product = Product.parseProductFromTextFile(curr);
                products.add(product);
            } catch (ZhongliException e) {
                System.out.println("Line Number " + lineNum + "has error: " + e.getMessage());
            }
        }

        return products;
    }

    /**
     * Formats the file from the given filePath and transform it into an Product List object
     *
     */
    public ProductList initializeProductList(Ui ui) {
        ArrayList<Product> products = new ArrayList<>();

        try {
            products = this.getProductsFromFile(ui);
        } catch (FileNotFoundException e) {
            ui.displayExceptionMessage(e.getMessage());
        }

        return new ProductList(products);
    }

    /**
     * Write the products in product list to the file
     *
     */
    public void writeProductListToFile(ProductList productList) throws IOException {
        FileWriter fileWriter = new FileWriter(filePath, false);

        for (int i = 0; i < productList.getSize(); i++) {
            try {
                Product product = productList.getProduct(i);
                fileWriter.write(product.convertToText());
            } catch (ZhongliException e) {
                System.out.println(e.getMessage());
            }
        }

        fileWriter.close();
    }
}

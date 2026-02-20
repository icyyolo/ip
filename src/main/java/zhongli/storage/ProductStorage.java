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
 * Manages persistent storage of products. Handles reading products from and writing
 * products to a text file, with error handling for malformed product entries.
 *
 */
public class ProductStorage extends Storage {

    public ProductStorage(String filePath) {
        super(filePath);
    }

    /**
     * Reads products from the storage file and parses them into Product objects.
     * Skips any lines that fail to parse and logs the error with the corresponding line number.
     *
     * @param ui The UI object used to display error messages.
     * @return An ArrayList of successfully parsed Product objects.
     * @throws FileNotFoundException If the storage file cannot be found or accessed.
     */
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
     * Reads products from the storage file and returns them as a ProductList object.
     * If the file cannot be found, an empty ProductList is returned and an error message is displayed.
     *
     * @param ui The UI object used to display error messages.
     * @return A ProductList containing all successfully loaded products.
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
     * Writes all products from the provided ProductList to the storage file.
     * Overwrites the existing file content. Skips any products that fail to convert to text format.
     *
     * @param productList The ProductList containing products to be persisted.
     * @throws IOException If an IO error occurs while writing to the file.
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

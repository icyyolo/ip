package zhongli.product;

import java.util.ArrayList;

import zhongli.zhongliexception.ZhongliException;

/**
 * Represents an ArrayList of products.
 *
 */
public class ProductList {
    private ArrayList<Product> products;

    public ProductList() {
        this.products = new ArrayList<>();
    }

    public ProductList(ArrayList<Product> products) {
        this.products = products;
    }

    /**
     * Checks if the products list is empty.
     *
     */
    public boolean isEmpty() {
        return this.products.isEmpty();
    }

    /**
     * Checks if the index given is within the array size.
     *
     * @param index - the index the user want to access.
     * @throws ZhongliException - if the index is not within the range, or there is no items in the array.
     *
     */
    public void checkValidRange(int index) throws ZhongliException {
        if (isEmpty()) {
            throw new ZhongliException("The list is empty, please add some tasks before deleting");
        } else if (index < 0 || index >= products.size()) {
            throw new ZhongliException("This index does not exist. The range should be between 1 and " + getSize());
        }
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public Product getProduct(int index) throws ZhongliException {
        checkValidRange(index);
        return this.products.get(index);
    }

    /**
     * Deletes the product at the index in the array
     *
     */
    public void deleteProduct(int index) throws ZhongliException {
        checkValidRange(index);
        this.products.remove(index);
    }

    public int getSize() {
        return products.size();
    }

    /**
     * Returns a string representation of each product in the product list
     *
     */
    public String listProduct() {
        StringBuilder list = new StringBuilder();
        for (int i = 0; i < this.getSize(); i++) {
            list.append(i + 1)
                    .append(") ")
                    .append(this.products.get(i).toString())
                    .append("\n");
        }
        return list.toString();
    }
}

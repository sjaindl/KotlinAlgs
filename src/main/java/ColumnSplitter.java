public class ColumnSplitter {
    
    String split(String text, int columnLen) {
        StringBuilder output = new StringBuilder();
        StringBuilder temp = new StringBuilder();

       for (int index = 0; index < text.length(); index++) {
            char curChar = text.charAt(index);

            if (curChar == ' ' && temp.length() >= columnLen) {
                temp.append(System.lineSeparator());
                output.append(temp.toString());
                temp = new StringBuilder();
            } else if (curChar != ' ' || !temp.isEmpty()) {
                temp.append(curChar);
            }
        }

        if (!temp.isEmpty()) {
            output.append(temp.toString());
        }

        return output.toString();
    }

    public static void main(String[] args) {
        ColumnSplitter splitter = new ColumnSplitter();
        String text = "test test Google Goog x a    b c d efa";
        System.out.println(splitter.split(text, 10));
    }
}

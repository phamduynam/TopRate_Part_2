package com.company;

import java.io.*;
import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    final static String specialCharacters = ".,<>?';:`~!@#$%^&*()_-+|/[]— \" \n";
    /*
    * Read file text 5MB
    * Counts the number of occurrences of a word
    * Words distinguished by : . ; , ; Space
    * */
    public static void main(String[] args) {
	// write your code here


        // Đọc input và xử lí data rồi đáp vào file output
//        preProcess();

        HashMap<String,Long> data = new HashMap<>();

        data = readOutput("output.txt");


        // Sắp xếp HashMap dựa trên value

        Map<String,Long> dataSorted = sortMap(data);

        // Hiển thị HashMap đã được sắp xếp
        for(Map.Entry<String, Long> entry : dataSorted.entrySet()){
            System.out.println( entry.getKey() + " " + entry.getValue());
       }

<<<<<<< HEAD
       // Tìm kiếm
=======
      // Tìm kiếm
>>>>>>> 7e20e4aa9fd09112321d5e2ea139953060724a8e
        searchByWords(data);

    }
    public static  void preProcess(){
        HashMap<String, Long> dataList = new HashMap<String,Long>();

        try{
            FileReader file = new FileReader("input.txt");
            BufferedReader readFile = new BufferedReader(file);

            FileWriter fileOut = new FileWriter("output.txt");
            BufferedWriter writeOutput = new BufferedWriter(fileOut);

            String i;
            while ( (i = readFile.readLine()) != null){
//                System.out.println( i);
                // Nếu chuỗi không rỗng và không phải kí tự đặc biệt
                if(checkStringValid(i)){

                    // Tách thành từng mảng string con.
                    String[] words = i.split(" ");
//
//                    System.out.println(Arrays.toString(words));

                    // Đẩy từng từ vào hasmap
                    for(int j = 0 ; j < words.length ; j ++){
                        if(checkStringValid(words[j].trim())){
                            // Chuẩn hóa từ
                            words[j] = words[j].trim();
                            String temp = standarString(words[j].trim());
                            if(temp.equals("") == false && specialCharacters.equals(temp) == false && temp.equals(" ") == false &&temp != null){
                                // Nếu chưa có thì thêm vào
                                if(dataList.get(temp) == null){
                                    dataList.put(temp,(long) 1);
                                }else{// Nếu có rồi thì cộng thêm số lượng
                                    dataList.put(temp,dataList.get(temp) + 1);
                                }
                            }

                        }
                    }
                }
            }
            readFile.close();
            file.close();

            for (Map.Entry<String, Long> entry : dataList.entrySet()){
                fileOut.write(entry.getKey() + " " + entry.getValue() + "\n");
                System.out.println(entry.getKey() + " " + entry.getValue());
            }

            writeOutput.close();
            fileOut.close();

        }catch (IOException ex){
            ex.printStackTrace();
        }
    }

    public static String standarString(String s){
        // Kiểm tra đến khi nào không có kí tự thừa ở 2 đầu
        while(specialCharacters.contains(Character.toString(s.charAt(0))) || specialCharacters.contains(Character.toString(s.charAt(s.length() - 1)))){
            // Nếu đầu chuỗi chứa kí tự đặc biệt thì xóa đi
            if(specialCharacters.contains(Character.toString(s.charAt(0)))){
                s = removeCharAt(s,0);
            }
            if(s.equals("")){
                return s;
            }
            // Nếu cuối chuỗi chứa kí tự đặc biệt thì xóa đi
            if(specialCharacters.contains(Character.toString(s.charAt(s.length() - 1)))){
                s = removeCharAt(s,s.length() - 1);
            }
            if(s.equals("")){
                return s;
            }
        }
        // Chuyển hết về chữ thường.
        return s.toLowerCase();
    }

    public static String removeCharAt(String s, int index){
        return s.substring(0,index) + s.substring(index+1);
    }

    public static boolean checkStringValid(String s){
        if(specialCharacters.contains(s) == false && s.equals("") == false){
            return true;
        }
        return false;
    }

    public static HashMap<String,Long> readOutput(String s){
        HashMap<String,Long> data = new HashMap<>();
        FileReader file = null;
        try {
            file = new FileReader(s);
            BufferedReader readFile = new BufferedReader(file);
            String str ;
            while((str = readFile.readLine()) != null ){
                String[] strArr  = str.split(" ");
                data.put(strArr[0],Long.parseLong(strArr[1]));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return data;
    }

    public static Map<String,Long> sortMap(Map<String,Long> data){
        // Sắp xếp và đẩy vào một LinkedHashMap
        Map<String, Long> result = data.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new)
                );
        return result;
    }

    public static SortedMap<String, Long> getByPrefix( NavigableMap<String, Long> myMap,String prefix ) {
        return myMap.subMap( prefix, prefix + Character.MAX_VALUE );
    }

    public static void searchByWords(Map<String, Long> data){
        TreeMap<String,Long> treeMap = new TreeMap<>(data);

        String keySearch = "";
        Scanner sc = new Scanner(System.in);
        while(keySearch.equals("null") == false){
            System.out.println("Nhập từ tìm kiếm : ");

            keySearch = sc.nextLine();

            long start = System.currentTimeMillis();

            // Danh sách bắt đầu bằng keySearch
            SortedMap<String,Long> listTemp = getByPrefix(treeMap,keySearch.toLowerCase());
            // Sắp xếp danh sách chuyển về Map
            Map<String,Long> listSearch = sortMap(listTemp);


            //Lấy danh sách các key
            List<String> key = new ArrayList<String>(listSearch.keySet());


            System.out.println("Danh sách tất cả từ có liên quan đã được sắp xếp");
            for(Map.Entry<String, Long> entry : listSearch.entrySet()){
                System.out.println( entry.getKey() + " " + entry.getValue());
            }


            if(key.size() == 0 )
            {
                System.out.println("Không tìm thấy kết quả !!!");
            }
            else if(key.size() < 5)
            {

                System.out.println("Top " + key.size() +" Search : ");
                for (int i = key.size() - 1 ; i >= 0 ; i --){
                    System.out.println("Key : " + key.get(i) + "  Value : " + listSearch.get(key.get(i)) );
                }
            }else
                {
                System.out.println("Top 5 Search : ");
                for(int i = key.size() - 1 ; i >= key.size() - 6 ; i --){
                    System.out.println("Key : " + key.get(i) + "  Value : " + listSearch.get(key.get(i)) );
                }
            }

            long end = System.currentTimeMillis();
            long t = end - start;
            System.out.println("Tổng thời gian tìm kiếm : " + t + " millisecond");
        }
    }

}

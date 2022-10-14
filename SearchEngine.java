import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

public class SearchEngine implements URLHandler{
    ArrayList<String> database = new ArrayList<String>();

    @Override
    public String handleRequest(URI url) {
        if (url.getPath().equals("/")) {
            String str = "Use \"/add?s=\" to add to the database \n and use \"/search?s=\" to search the database";
            if (database.size() > 0) {
                str += "\n Current database: \n";
                for (int i = 0; i < database.size(); i ++) {
                    str += database.get(i) + "\n";
                }
            }
            return str;
        } else if (url.getPath().contains("/add")) {
            String[] parameters = url.getQuery().split("=");
            if (parameters[0].equals("s")) {
                if (parameters.length < 2) {
                    return "Please add a string!";
                } else if (!database.contains(parameters[1])) {
                    database.add(parameters[1]);
                    return "Added \"" + parameters[1] + "\" to the database";
                }
                return "Entry already exists!";
                
            }
        } else if (url.getPath().contains("/search")) {
            String[] parameters = url.getQuery().split("=");
            if (parameters[0].equals("s")) {
                if (parameters.length < 2) {
                    return "Please search for a string!";
                } 
                String results = "";
                for (int i = 0; i < database.size(); i ++) {
                    if (database.get(i).contains(parameters[1])) {
                        results += database.get(i) + "\n";
                    }
                }
                if (results.equals("")) {
                    return "No results found";
                }
                return results;
            }
            
        }
        return "404 Not Found!";
    }

    
    
}

class SearchMain {
        
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        Server.start(Integer.parseInt(args[0]), new SearchEngine());
    }
}

package jrails;
import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class Model {

    public int id = 0;
    private static boolean is_header = false;
    private String curr_instance_name="";

    public File createNewFile(String curr_class_name, int idx) {
        File curr_file = new File("db/" + curr_class_name);
        try {
            if(!curr_file.exists()){
                curr_file.mkdirs();
                File new_file = new File("db/" + curr_class_name + "/" + idx + ".txt");
                if(!new_file.exists()) {
                    new_file.createNewFile();
                    return new_file;
                }
            } else {
                File new_file = new File("db/" + curr_class_name + "/" + idx + ".txt");
                if(!new_file.exists()) {
                    new_file.createNewFile();
                    return new_file;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int get_max_id(String curr_class_name) {
        File curr_file = new File("db/" + curr_class_name);
        File[] files = curr_file.listFiles();
        int curr_max_idx = 0;

        for(File f : files){
            int curr_val = Integer.parseInt(f.getName().split("\\.", 2)[0]);
            if(curr_val > curr_max_idx) {
                curr_max_idx = curr_val;
            }
        }
        return curr_max_idx;
    }
    public void save() {
        String header = "id,";
        String row_value = "";
        String curr_class_name = Model.this.getClass().getSimpleName();
        curr_instance_name = curr_class_name;
        Field[] fields = Model.this.getClass().getDeclaredFields();
        for(int i = 0; i < fields.length; i++){
            if(i == 0) {
                try {
                    if(fields[i].getName() != "id" || fields[i].get(Model.this) == null) {
                        row_value += this.id + ",";
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            fields[i].setAccessible(true);
            String curr_data_type = fields[i].getType().getName();
            header +=  fields[i].getName() + ",";

            try {
                if(fields[i].isAnnotationPresent(Column.class)) {
                    if(curr_data_type == "java.lang.String" || curr_data_type == "int" || curr_data_type == "boolean"){
                        if(fields[i].get(Model.this) == null) {
                            row_value += "" + ",";
                        }
                        row_value += fields[i].get(Model.this) + ",";
                    } else{
                        throw new UnsupportedOperationException();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        header = header.substring(0, header.length() - 1);
        row_value = row_value.substring(0, row_value.length() - 1);
        String output = "";
        if(row_value.contains(System.getProperty("line.separator"))) {
            output = row_value;
        } else {
            output = row_value + System.getProperty("line.separator");
        }
        String s = "";
        boolean is_updating = false;
        File curr_file = null;

        if(this.id != 0){
            if(find(Model.this.getClass(), id) != null) {
                row_value = "";
                Model curr_model = find(Model.this.getClass(), id);
                Field[] curr_fields = curr_model.getClass().getDeclaredFields();
                for(Field f : curr_fields) {
                    try {
                        row_value += f.get(Model.this) + ",";
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                row_value = row_value.substring(0, row_value.length() - 1);
                output = row_value + System.getProperty("line.separator");
                s = (id) + "," + output;
                is_updating = true;
                curr_file = new File("db/" + curr_model.getClass().getSimpleName() +
                        "/" + (id) + ".txt");
            } else {
                s = id + output.substring(1);
            }
        } else {
            File check_files = new File("db/" + curr_class_name);
            if(check_files.exists()){
                this.id = get_max_id(curr_class_name);
            }
            this.id++;
            s = this.id + output.substring(1);
        }

        FileWriter fstream = null;

        try {
            if(!is_updating){
                curr_file = createNewFile(curr_class_name, this.id);
                try {
                    fstream = new FileWriter(curr_file, true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    fstream = new FileWriter(curr_file, false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if(curr_file != null) {
                fstream = new FileWriter(curr_file, true);
                BufferedWriter out = new BufferedWriter(fstream);
                if(curr_file.exists()){
                    if(!is_header){
                        fstream.write(header + System.getProperty("line.separator"));
                    }
                }
                out.write(s);
                out.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int id() {
        return this.id;
    }
    private void set_id(int val) {
        this.id = val;
    }

    public static <T> T find(Class<T> c, int s_id) {
        String file_name = "";
        String curr_class_name = c.getSimpleName();
        BufferedReader reader;
        int header = 0;
        File curr_directory = new File("db/" + curr_class_name);
        File[] all_files = curr_directory.listFiles();

        if(all_files != null){
            for(File f : all_files) {
                file_name = f.getName();
                if (Integer.parseInt(file_name.substring(0, 1)) == s_id) {
                    try {
                        reader = new BufferedReader(new FileReader("db/" + curr_class_name + "/" + file_name));
                        String line = reader.readLine();
                        while (line != null) {
                            if (header > 0) {
                                line = reader.readLine();
                                String[] columns = line.split(",");
                                Constructor<T> cons = c.getConstructor();
                                Model curr_class = (Model) cons.newInstance();
                                Field[] fields = c.getDeclaredFields();
                                int column_idx = 1;
                                for (Field curr_field : fields) {
                                    if (curr_field.getType().equals(int.class)) {
                                        curr_field.setInt(curr_class, Integer.parseInt(columns[column_idx]));
                                    } else if (curr_field.getType().equals(boolean.class)) {
                                        curr_field.setBoolean(curr_class, Boolean.parseBoolean(columns[column_idx]));
                                    } else {
                                        curr_field.set(curr_class, columns[column_idx]);
                                    }
                                    column_idx += 1;
                                }
                                curr_class.set_id(Integer.parseInt(columns[0]));
                                return (T) curr_class;
                            }
                            header += 1;
                        }
                        reader.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }}
        return null;
    }

    public static <T> List<T> all(Class<T> c) {
        List<Model> output = new ArrayList<>();
        BufferedReader reader;
        String curr_class_name = c.getSimpleName();
        int header = 0;
        String file_name = "";

        File curr_directory = new File("db/" + curr_class_name);
        File[] all_files = curr_directory.listFiles();
        if(all_files != null) {
            for (File f : all_files) {
                file_name = f.getName();
                try {
                    reader = new BufferedReader(new FileReader("db/" + curr_class_name + "/" + file_name));
                    String line = reader.readLine();
                    while (line != null) {
                        if (header > 0) {
                            line = reader.readLine();
                            if(line != null) {
                                String[] columns = line.split(",");
                                Constructor<T> cons = c.getConstructor();
                                Model curr_class = (Model) cons.newInstance();
                                Field[] fields = c.getDeclaredFields();
                                int column_idx = 1;
                                for (Field curr_field : fields) {
                                    if (curr_field.getType().equals(int.class)) {
                                        curr_field.setInt(curr_class, Integer.parseInt(columns[column_idx]));
                                    } else if (curr_field.getType().equals(boolean.class)) {
                                        curr_field.setBoolean(curr_class, Boolean.parseBoolean(columns[column_idx]));
                                    } else {
                                        curr_field.set(curr_class, columns[column_idx]);
                                    }
                                    column_idx += 1;
                                    curr_class.set_id(Integer.parseInt(columns[0]));
                                }
                                output.add(curr_class);
                            }
                        }
                        header += 1;
                    }
                    reader.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return (List<T>) output;

    }

    public void destroy() {
        Class<?> curr_class = this.getClass();
        String class_name = curr_class.getSimpleName();
        Field[] all_fields = curr_class.getDeclaredFields();
        String row_data = "";
        int header = 0;

        for(Field field : all_fields) {
            try {
                Object val = field.get(Model.this);
                row_data += val + ",";
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        row_data = row_data.substring(0, row_data.length()-1);
        File curr_dir = new File("db/" + curr_class.getSimpleName());
        File[] all_files = curr_dir.listFiles();
        if(all_files != null) {
            for (File f : all_files) {

                try {
                    BufferedReader reader = new BufferedReader(new FileReader("db/" + curr_class.getSimpleName() +
                            "/" + f.getName()));
                    String line = reader.readLine();
                    while (line != null) {
                        if (header > 0) {
                            line = reader.readLine();
                            if (line.contains(row_data)) {
                                f.delete();
                            }
                        }
                        header += 1;
                    }
                    reader.close();
                } catch (Exception e) {
                }
            }
        }
        else {
            throw new RuntimeException();
        }
    }

    public static void reset() {
        File curr_path = new File("db/");
        File[] all_class_instances = curr_path.listFiles();

        if(all_class_instances != null) {
            try {
                for (File curr_dir : all_class_instances) {
                    for (File file : curr_dir.listFiles()) {
                        FileWriter file_object = new FileWriter(file.getName(), false);
                        PrintWriter print_object = new PrintWriter(file_object, false);
                        print_object.flush();
                        print_object.close();
                        file_object.close();
                        file.delete();
                    }
                    curr_dir.delete();
                }
                curr_path.delete();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
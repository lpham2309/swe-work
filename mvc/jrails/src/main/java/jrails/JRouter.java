package jrails;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JRouter {

    private List<Map<String, String>> route_mapping = new ArrayList<>();
    public void addRoute(String verb, String path, Class clazz, String method) {
        Map<String, String> curr_route = new HashMap<>();
        curr_route.put(verb + path, clazz.getName() +"#"+method);
        route_mapping.add(curr_route);
    }

    public String getRoute(String verb, String path) {
        String curr_path = verb+path;
        try {
            for(Map<String, String> curr_route : route_mapping) {
                if(curr_route.containsKey(verb+path)){
                    return curr_route.get(verb+path);
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public Html route(String verb, String path, Map<String, String> params) {
        try {
            String curr_route = getRoute(verb, path);
            if(!curr_route.isEmpty()) {
                int get_delimeter_idx = curr_route.lastIndexOf("#");
                Class<?> c = Class.forName(curr_route.substring(0, get_delimeter_idx));
                String a = curr_route.substring(get_delimeter_idx+1);
                Method curr_method = c.getMethod(curr_route.substring(get_delimeter_idx+1), Map.class);
                Html ret = (Html) curr_method.invoke(null, params);
                return ret;
            }
        } catch(Exception e) {
            throw new UnsupportedOperationException();
        }
        return null;
    }
}

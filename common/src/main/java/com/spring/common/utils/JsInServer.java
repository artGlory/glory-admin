package com.spring.common.utils;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

public class JsInServer {

    public static void main(String[] arg) throws IOException {
        String data = "我是中古人";

        ScriptEngineManager maneger = new ScriptEngineManager();
        ScriptEngine engine = maneger.getEngineByName("JavaScript");

        Reader scriptReader = new InputStreamReader(
                new FileInputStream("D:\\ProjectData1\\vue-lean\\guaji_enzuo\\deflate.js"), "UTF-8");
//                JsInServer.class.getResourceAsStream("deflate.js"));
        if (engine != null) {
            try {
                // JS引擎解析文件
                engine.eval(scriptReader);
                if (engine instanceof Invocable) {
                    Invocable invocable = (Invocable) engine;
                    // JS引擎调用方法
                    Object result = invocable.invokeFunction("zip_deflate", data);
                    result = invocable.invokeFunction("base64_encode", result);
                    System.out.println("The result is: " + result);
                }
            } catch (ScriptException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } finally {
                scriptReader.close();
            }
        } else {
            System.out.println("ScriptEngine create error!");
        }
    }
}
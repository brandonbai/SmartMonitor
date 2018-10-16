package com.github.brandonbai.smartmonitor;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.JsePlatform;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LuaJTest {

    @Test
    public void test() {
        int size = 100000;

        Globals globals = JsePlatform.standardGlobals();
        long start = System.currentTimeMillis();
        for (int i = 0; i < size; i++) {
            String luaStr = String.format("return %d > %d", i, i+1);
            LuaValue chunk = globals.load(luaStr);
            chunk.call().toboolean();
        }
        long end = System.currentTimeMillis();
        System.out.printf("lua：%d ms\n", end - start);

        ScriptEngineManager sem = new ScriptEngineManager();
        ScriptEngine scriptEngine = sem.getEngineByName("JavaScript");
        start = System.currentTimeMillis();
        for (int i = 0; i < size; i++) {
            String jsStr = String.format("%d > %d", i, i+1);
            try{
                scriptEngine.eval(jsStr);
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        end = System.currentTimeMillis();
        System.out.printf("js：%d ms\n" ,end - start);

    }
}

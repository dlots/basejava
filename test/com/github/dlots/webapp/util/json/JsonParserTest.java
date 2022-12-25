package com.github.dlots.webapp.util.json;

import com.github.dlots.webapp.ResumeTestData;
import com.github.dlots.webapp.model.Resume;
import com.github.dlots.webapp.model.section.Section;
import com.github.dlots.webapp.model.section.TextSection;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class JsonParserTest {
    public static final String UUID_1 = "uuid1";

    public static final Resume R1 = ResumeTestData.getFilledResume(UUID_1, "Amogus");

    @Test
    public void testResume() throws Exception {
        String json = JsonParser.write(R1);
        System.out.println(json);
        Resume parsedResume  = JsonParser.read(json, Resume.class);
        Assert.assertEquals(R1, parsedResume);
    }

    @Test
    public void write() throws Exception{
        Section section1 = new TextSection("Objective1");
        String json = JsonParser.write(section1, Section.class);
        System.out.println(json);
        Section section2 = JsonParser.read(json, Section.class);
        Assert.assertEquals(section1, section2);
    }
}
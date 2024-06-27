package persistence;

import org.json.JSONObject;

// Source: JsonSerializationDemo, CPSC 210 course content
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}

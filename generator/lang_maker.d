import std.conv : to;
import std.string;

string pathBase;
string rootPath = "";
string targetFile;

string[string] jsonDictory;
string[] lineCaches;

void main(string[] args)
{
    // Init the args array without the exe.
    args = args[1 .. $];

    // Main start.
    targetFile = args[0];

    if (args.length > 1)
    {
        check_root(args[1]);
    }

    pathBase = rootPath ~ "src/main/resources/assets/crystaldream/lang/";

    read_file();
    match_and_write_to_file();
}

void check_root(string arg)
{
    if (!startsWith(arg, "isRoot="))
    {
        return;
    }

    auto argBoolean = to!bool(arg.replace("isRoot=", ""));
    if (!argBoolean)
    {
        rootPath = "../";
    }
}

void read_file()
{
    import std.file : read, readText, exists;
    import std.json;

    void read_target_file()
    {
        /* Read the target Json */
        auto targetPath = pathBase ~ targetFile ~ ".json";
        if (!exists(targetPath))
        {
            return;
        }
        const JSONValue[string] json = parseJSON(readText(targetPath), JSONOptions
                .escapeNonAsciiChars).object();

        string[string] jsonDict;
        foreach (JSONValue node; json.keys())
        {
            string key = node.str();
            jsonDict[key] = json[key].str();
        }

        jsonDictory = jsonDict;
    }

    void read_zh_file()
    {
        import std.container : Array;

        ubyte[] bytes = (cast(ubyte[]) read(pathBase ~ "zh_cn.json"));

        auto lines = Array!string();
        string cache;

        for (int i = 0; i < bytes.length; i++)
        {
            ubyte bt = bytes[i];
            if (bt != 13)
            {
                cache = cache ~ to!char(bt);
                continue;
            }

            if (cache.empty())
            {
                lines.insertBack("\n");
            }
            else
            {
                lines.insertBack(cache);
            }
            cache = "";
            i++;
        }

        if (!cache.empty())
        {
            lines.insertBack(cache);
        }

        lineCaches = lines.data();
    }

    read_target_file();
    read_zh_file();
}

void match_and_write_to_file()
{
    import std.file : write;
    import std.stdio : writeln;

    string builder = "{\n";
    foreach (string node; lineCaches)
    {
        node = node.replace("\"", "").replace(",", "");

        if (node == "\n")
        {
            builder ~= "\n";
            continue;
        }

        string[] kv = node.split(":");
        if (kv.length != 2)
        {
            continue;
        }

        string key = kv[0];
        string value;

        if (includeInArray(jsonDictory.keys(), key))
        {
            value = jsonDictory[key];
        }
        else
        {
            value = kv[1];
        }

        key = removeSpaceInHead(key);
        value = removeSpaceInHead(value);

        writeln(format("%s:%s", key, value));
        builder ~= "    \"" ~ key ~ "\": \"" ~ value ~ "\",\n";
    }

    builder = builder[0 .. $ - 2];
    builder ~= "\n}";

    write(pathBase ~ targetFile ~ ".json", builder);
}

string removeSpaceInHead(string key)
{
    while (key.startsWith(" "))
    {
        key = key[1 .. $];
    }

    return key;
}

bool includeInArray(T)(T[] arr, T obj)
{
    foreach (T key; arr)
    {
        if (key == obj)
        {
            return true;
        }
    }
    return false;
}
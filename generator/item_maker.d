import std.file;
import std.string;

string pathLangBase;
string pathModelBase;

string rootPath = "";

string[] items;

void main(string[] args)
{
    // Init the args array without the exe.
    args = args[1 .. $];

    // Main start.
    if (args.length >= 1)
    {
        check_root(args[0]);
    }

    pathLangBase = rootPath ~ "src/main/resources/assets/crystaldream/lang/";
    pathModelBase = rootPath ~ "src/main/resources/assets/crystaldream/models/item/";

    find_items();
    scan_and_generator();
}

void check_root(string arg)
{
    import std.conv : to;

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

void find_items()
{
    import std.json;
    import std.container : Array;

    const JSONValue[string] json = parseJSON(readText(pathLangBase ~ "zh_cn.json"), JSONOptions
            .escapeNonAsciiChars).object();

    auto arr = Array!string();

    foreach (JSONValue node; json.keys())
    {
        auto nodename = node.str();
        if (!nodename.startsWith("item."))
        {
            continue;
        }

        auto dat = nodename.split(".");
        if (dat.length < 2)
        {
            continue;
        }
        arr.insertBack(dat[2]);
    }

    items = arr.data();
}

void scan_and_generator()
{
    import std.stdio : writeln;

    foreach (string key; items)
    {
        auto filename = key ~ ".json";
        auto path = pathModelBase ~ filename;
        if (exists(path))
        {
            writeln("Pass: " ~ filename);
            continue;
        }

        string input = "";
        input ~= "{\n";
        input ~= "    \"parent\": \"minecraft:item/generated\",\n";
        input ~= "    \"textures\": {\n";
        input ~= "        \"layer0\": \"crystaldream:item/" ~ key ~ "\"\n";
        input ~= "    }\n";
        input ~= "}";

        writeln("Generator: " ~ filename);
        write(path, input);
    }
}

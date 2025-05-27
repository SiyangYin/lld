package org.example.lld.file;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

//abstract class Filter {
//    public abstract boolean apply(File file);
//}

interface Filter {
    boolean apply(File file);
}

class SizeFilter implements Filter {
    int min = 0;
    int max = Integer.MAX_VALUE;

    public SizeFilter(int min, int max) {
        this.min = min;
        this.max = max;
    }

    public boolean apply(File file) {
        return min < file.size && file.size < max;
    }
}

class TypeFilter implements Filter {
    String type;

    public TypeFilter(String type) {
        this.type = type;
    }

    public boolean apply(File file) {
        return type.equals(file.type);
    }
}

interface Operator {
    void apply(File file, List<File> files, List<Filter> filters);
}

class And implements Operator {
    public void apply(File file, List<File> files, List<Filter> filters) {
        for (Filter filter : filters) {
            if (!filter.apply(file)) return;
        }
        files.add(file);
    }
}

class Or implements Operator {
    public void apply(File file, List<File> files, List<Filter> filters) {
        for (Filter filter : filters) {
            if (filter.apply(file)) {
                files.add(file);
                return;
            }
        }
    }
}

class File {
    int size;
    String type;
    boolean isFile;
    List<File> children;
}

class FindCommand {

    public static void main(String[] args) {
        File file = new File();
        List<Filter> filters = List.of(new SizeFilter(5, Integer.MAX_VALUE), new TypeFilter("xml"));
        FindCommand command = new FindCommand();
        command.findFile(file, filters, new And());
        command.findFile(file, filters, new Or());
    }

    public List<File> findFile(File file, List<Filter> filters, Operator op) {
        List<File> files = new ArrayList<>();
        dfs(file, files, filters, op);
        return files;
    }

    public void dfs(File file, List<File> files, List<Filter> filters, Operator op) {
        if (file.isFile) {
            op.apply(file, files, filters);
//            switch (op) {
//                case "and":
//                    and(file, files, filters.stream().map(i -> (Predicate<File>) i::apply).toList());
//                    break;
//                case "or":
//                    or(file, files, filters.stream().map(i -> (Predicate<File>) i::apply).toList());
//                    break;
//            }
        } else {
            for (File f : file.children) dfs(f, files, filters, op);
        }
    }

//    public void and(File file, List<File> files, List<Predicate<File>> predicates) {
//        for (Predicate<File> predicate : predicates) {
//            if (!predicate.test(file)) return;
//        }
//        files.add(file);
//    }
//
//    public void or(File file, List<File> files, List<Predicate<File>> predicates) {
//        for (Predicate<File> predicate : predicates) {
//            if (predicate.test(file)) {
//                files.add(file);
//                return;
//            }
//        }
//    }
}

public class LLD_FileDesing {
}

/*
Logical and Maintainable

Imagine that you need to write code in a high level language like java, that does things similar to the find command.
I would like you to focus on 2 uses cases at first.

    Find all files over 5 MB somewhere under a directory.
    Find all XML files somewhere under a directory.
    ...
    ...
    AND OR find files > 5 Mb OR extension is XML

((file > 5Mb) or (file < 1Mb)) and (file ends in XML)

I would like you to create a library that lets me do this easily.
Keep in mind that these are just 2 uses cases and that the library should be flexible.



requirement:

class FileTree{

   String name;
   String Type;
   boolean isDirectory;
   int size;
   List<FileTree> childrens;
}

FilterNode{

    sizeGreaterThan - 4MB
    sizeL
    dsfs
    sizeequal = 7mb

}


Filter{


}

SizeFilter{

    GREATER, LESSER_THAN, EQUALS
}

// example
List<File> find(FileTree startLocation, Filter sizeFilter, List<File> output) {

    if(!startLocation.isDirectory) {


            boolean fileMatched =  filter(startLocation.size, sizeFilter, output);
            if(fileMatched) {
               output.add(startLocation);


        }
    } else {

        while(FileTree childsNode: startLocation.childrens) {

            find(childsNode, sizeFilter);
        }
    }
}


class Filter{




   boolean filter(FileTree file) {




   }
}



SizeFilter extends Filter{



boolean filter(FileTree file) {


}




}








 */
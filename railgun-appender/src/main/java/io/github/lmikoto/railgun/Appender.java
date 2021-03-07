package io.github.lmikoto.railgun;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.*;
import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import io.github.lmikoto.railgun.utils.CollectionUtils;
import lombok.SneakyThrows;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author liuyang
 * 2021/3/6 4:23 下午
 */
public class Appender {


    public String process(SimpleClass model,String oldCode){
        CompilationUnit unit = Optional.ofNullable(oldCode).map(StaticJavaParser::parse).orElse(new CompilationUnit());
        TypeDeclaration<?> type = Optional.ofNullable(unit.getTypes()).flatMap(NodeList::getFirst).orElseGet(() ->{
            TypeDeclaration declaration = new ClassOrInterfaceDeclaration();
            unit.addType(declaration);
            return declaration;
        });

        buildPackage(unit,JavaUtils.getPackageName(model.getName()));

        buildClass(type,model);

        buildExtend(type,model.getExtend(),model.getImports());

        buildClassAnnotations(type,model.getAnnotations(),model.getImports());

        buildFiled(type,model.getFields(),model.getImports());

        buildMethod(type,model.getMethods(),model.getImports());

        buildImport(unit,model.getImports());
        return unit.toString();
    }

    private void buildFiled(TypeDeclaration<?> type, LinkedHashMap<String, SimpleClass> fields, Set<String> imports) {
        if(!(type instanceof ClassOrInterfaceDeclaration)){
            return;
        }

        if(Objects.isNull(fields)){
            return;
        }

        ClassOrInterfaceDeclaration declaration = (ClassOrInterfaceDeclaration)type;
        Set<String> existed = declaration.getFields().stream()
                .map(FieldDeclaration::getVariables).map(i -> i.get(0)).map(VariableDeclarator::getNameAsString).collect(Collectors.toSet());

        Set<Map.Entry<String, SimpleClass>> entries = fields.entrySet();
        for(Map.Entry<String, SimpleClass> entry: entries){
            if(existed.contains(entry.getKey())){
                continue;
            }
            SimpleClass filedType = entry.getValue();
            declaration.addField(filedType.getSimpleName(),entry.getKey());
        }
    }

    private void buildMethod(TypeDeclaration<?> type, List<SimpleClass.SimpleMethod> methods, Set<String> imports) {
        if(!(type instanceof ClassOrInterfaceDeclaration)){
            return;
        }
        if(CollectionUtils.isEmpty(methods)){
            return;
        }
        ClassOrInterfaceDeclaration declaration = (ClassOrInterfaceDeclaration)type;
        // todo 支持重载
        Set<String> existed = declaration.getMethods().stream().map(MethodDeclaration::getNameAsString).collect(Collectors.toSet());
        methods.forEach(m->{
            if(!existed.contains(m.getName())){
                MethodDeclaration methodDeclaration = new MethodDeclaration();
                methodDeclaration.setName(m.getName());
                String returnType = Optional.ofNullable(m.getType()).map(SimpleClass::getSimpleName).orElse("void");
                methodDeclaration.setType(returnType);
                methodDeclaration.setModifiers(Modifier.Keyword.PUBLIC);

                if(Objects.nonNull(m.getParams())){
                    Set<Map.Entry<String, SimpleClass>> entries = m.getParams().entrySet();
                    for (Map.Entry<String, SimpleClass> entry: entries){
                        methodDeclaration.addParameter(entry.getValue().getSimpleName(),entry.getKey());
                    }
                }

                if(CollectionUtils.isNotEmpty(m.getAnnotations())){
                    for(SimpleClass.SimpleAnnotation simpleAnnotation: m.getAnnotations()){
                        AnnotationExpr annotationExpr = StaticJavaParser.parseAnnotation(simpleAnnotation.getExpr());
                        methodDeclaration.addAnnotation(annotationExpr);
                    }
                }

                if(CollectionUtils.isNotEmpty(m.getLine())){
                    BlockStmt body = new BlockStmt();
                    NodeList<Statement> statements = new NodeList<>();
                    for(String line: m.getLine()){
                        Statement statement = StaticJavaParser.parseStatement(line);
                        statements.add(statement);
                    }
                    body.setStatements(statements);
                    methodDeclaration.setBody(body);
                }

                type.addMember(methodDeclaration);
            }
        });

    }

    private void buildExtend(TypeDeclaration<?> type, List<SimpleClass> extend, Set<String> imports) {
        if(!(type instanceof ClassOrInterfaceDeclaration)){
            return;
        }
        if(CollectionUtils.isEmpty(extend)){
            return;
        }
        ClassOrInterfaceDeclaration declaration = (ClassOrInterfaceDeclaration)type;
        Set<String> existed = declaration.getExtendedTypes().stream().map(ClassOrInterfaceType::getNameAsString).collect(Collectors.toSet());
        extend.forEach(s->{
            String simpleName = JavaUtils.getSimpleName(s.getName());
            if(!existed.contains(simpleName)){
                declaration.addExtendedType(simpleName);
            }
        });
    }

    private void buildClassAnnotations(TypeDeclaration<?> type, List<SimpleClass.SimpleAnnotation> annotations, Set<String> imports) {
        if(CollectionUtils.isEmpty(annotations)){
            return;
        }
        NodeList<AnnotationExpr> ans = Optional.ofNullable(type.getAnnotations()).orElse(new NodeList<>());
        Set<String> existed = ans.stream().map(AnnotationExpr::getNameAsString).collect(Collectors.toSet());
        annotations.forEach(a->{
            if(!existed.contains(a.getSimpleName())){
                AnnotationExpr expr = StaticJavaParser.parseAnnotation(a.getExpr());
                type.addAnnotation(expr);
                imports.add(a.getName());
            }
        });
    }

    private void buildImport(CompilationUnit unit, Collection<String> imports) {
        if(CollectionUtils.isEmpty(imports)){
            return;
        }
        NodeList<ImportDeclaration> importDeclarations = Optional.ofNullable(unit.getImports()).orElse(new NodeList<>());
        Set<String> existed = importDeclarations.stream().map(ImportDeclaration::getNameAsString).collect(Collectors.toSet());

        imports.stream().distinct().forEach(i->{
            if(!existed.contains(i)){
                ImportDeclaration importDeclaration = new ImportDeclaration(i,false,false);
                importDeclarations.add(importDeclaration);
            }
        });
        unit.setImports(importDeclarations);
    }

    private void buildPackage(CompilationUnit unit, String packageName) {
        if(Objects.isNull(unit.getPackageDeclaration()) || !unit.getPackageDeclaration().isPresent()){
            PackageDeclaration packageDeclaration = new PackageDeclaration();
            packageDeclaration.setName(packageName);
            unit.setPackageDeclaration(packageDeclaration);
        }
    }

    private void buildClass(TypeDeclaration<?> type, SimpleClass model) {
        if(Objects.isNull(type.getName()) || "empty".equals(type.getNameAsString())){
            type.setName(JavaUtils.getSimpleName(model.getName()));
            type.setModifiers(Modifier.Keyword.PUBLIC);
        }
    }

    public String process(String config,String oldCode){
        SimpleClass model = JsonUtils.fromJson(config,new JsonUtils.TypeReference<SimpleClass>() {
        });
        return process(model,oldCode);
    }

    @SneakyThrows
    public void fullProcess(String config,String path){
        File file = new File(path);
        String oldCode = null;
        if(file.exists()){
            oldCode = new String(Files.readAllBytes(Paths.get(path)));
        }
        String code = process(config,oldCode);
        Files.write(Paths.get(path),code.getBytes());
    }
}

package test.com.anyun.cloud.demo.api.management;

import com.anyun.cloud.demo.api.management.raml.DefaultApiRamlParser;
import com.anyun.cloud.demo.api.management.raml.RamlApiRamlParser;
import com.anyun.cloud.demo.common.etcd.spi.entity.api.ApiEntity;
import org.junit.Before;
import org.junit.Test;
import org.raml.v2.api.model.v10.api.Api;
import org.raml.v2.api.model.v10.bodies.Response;
import org.raml.v2.api.model.v10.datamodel.ObjectTypeDeclaration;
import org.raml.v2.api.model.v10.datamodel.TypeDeclaration;
import org.raml.v2.api.model.v10.methods.Method;
import org.raml.v2.api.model.v10.resources.Resource;

import java.io.File;
import java.io.FilenameFilter;
import java.util.List;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 31/05/2017
 */
public class ApiRamlParserTest extends BaseTest {
    private static final String EXT_DIR = "/Users/twitchgg/Develop/Projects/hohot-cloud-demo/doc-api/example1";
    private static final String ENCODING = "utf-8";
    private File ramlDir;
    private File[] ramlFiles = null;
    private RamlApiRamlParser parser;

    @Before
    public void before() {
        parser = new DefaultApiRamlParser();
        ramlDir = new File(EXT_DIR);
        ramlFiles = ramlDir.listFiles(new TestFileFilter());
    }

    @Test
    public void test1() throws Exception {
        System.out.println(ramlDir.getAbsoluteFile());
        for (File file : ramlFiles) {
            Api api = parser.withEncoding(ENCODING).withRamlFile(file).buildV10Api();
            deploy(api, file);
        }
    }

    @Test
    public void test2() throws Exception {
        System.out.println(ramlDir.getAbsoluteFile());
        for (File file : ramlFiles) {
            ApiEntity api = parser.withEncoding(ENCODING).withRamlFile(file).buildApi();
            System.out.println(api);
        }
    }

    private void deploy(Api api, File file) throws Exception {
        System.out.println("Parse file [" + file.getName() + "] ...");
        System.out.println("Api base uri: " + api.baseUri().value());
        System.out.println("description: " + api.description().value());
        System.out.println("documentation size : " + api.documentation().size());
        System.out.println("=================================resources deploy==================================");
        deployResources(api.resources(), api);
    }

    private void deployResources(List<Resource> resources, Api api) throws Exception {
        for (Resource resource : resources) {
            if (resource.resources().size() != 0)
                deployResources(resource.resources(), api);
            deployResource(resource, api);
        }
    }

    private void deployResource(Resource resource, Api api) throws Exception {
        System.out.println("Path: " + resource.resourcePath());
        System.out.println("Name: " + resource.displayName().value());
        System.out.println("Desc: " + resource.description().value());
        Method method = resource.methods().get(0);
        displayMethod(method);
        displayRequestBody(method, api);
        displayResponses(method, api);
        System.out.println("----------------------------------------------------------------------------------");
    }

    private void displayRequestBody(Method method, Api api) {
        if (!method.method().equals("post") && !method.method().equals("put"))
            return;
        System.out.println("****************************Request body********************************");
        TypeDeclaration body = method.body().get(0);
        System.out.println("    Content Type: " + body.name());
        System.out.println("    Type Name: " + body.type());

        ObjectTypeDeclaration type = null;
        for (TypeDeclaration typeDeclaration : api.types()) {
            if (typeDeclaration.name().equals(body.type())) {
                type = (ObjectTypeDeclaration) typeDeclaration;
                break;
            }
        }
        if (type == null)
            return;
        displayType(type.properties());
    }

    private void displayType(List<TypeDeclaration> properties) {
        for (TypeDeclaration prop : properties) {
            System.out.println("      ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
            System.out.println("      Prop Type:" + prop.type());
            System.out.println("      Prop Required:" + prop.required());
            if (prop.example() != null)
                System.out.println("      Prop Example:" + prop.example().value());
        }
    }

    private void displayMethod(Method method) {
        System.out.println("Method Name: " + method.method());
        for (TypeDeclaration typeDeclaration : method.queryParameters()) {
            System.out.println("  ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
            System.out.println("  Param Name: " + typeDeclaration.name());
            System.out.println("  Param Desc: " + typeDeclaration.description().value());
            System.out.println("  Param Type: " + typeDeclaration.type());
            System.out.println("  Param Required: " + typeDeclaration.required());
            if (typeDeclaration.example() != null)
                System.out.println("  Param Example: " + typeDeclaration.example().value());
        }
    }

    private void displayResponses(Method method, Api api) {
        System.out.println("^^^^^^^^^^^^^^^^^^Response^^^^^^^^^^^^^^^^^^^^^^^^");
        for (Response response : method.responses()) {
            System.out.println("Response code: " + response.code().value());
            for (TypeDeclaration body : response.body()) {
                System.out.println("Body Content Type: " + body.name());
                System.out.println("Body Type: " + body.type());
                ObjectTypeDeclaration type = null;
                for (TypeDeclaration typeDeclaration : api.types()) {
                    if (typeDeclaration.name().equals(body.type())) {
                        type = (ObjectTypeDeclaration) typeDeclaration;
                        break;
                    }
                }
                if (type == null)
                    return;
                displayType(type.properties());
            }
            System.out.println("**************************************************");
        }
    }

    private static class TestFileFilter implements FilenameFilter {
        @Override
        public boolean accept(File dir, String name) {
            if (name.endsWith(".raml"))
                return true;
            return false;
        }
    }
}

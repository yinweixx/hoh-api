package test.com.anyun.common.service;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Ref;

import java.util.Collection;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 17/06/2017
 */
public class GitTestMain {
    public static void main(String[] args) throws Exception {
        String REMOTE_URL = "http://git.dev.hohhot.ga.gov/pgis/service-example1.git";
//        File localPath = new File("/Users/twitchgg/Develop/temp/hohot-cloud-demo/git/example2");
//        FileUtil.rm(localPath.getPath(),true);
//        System.out.println("Cloning from " + REMOTE_URL + " to " + localPath);
//        Git git = Git.cloneRepository()
//                .setURI(REMOTE_URL)
//                .setDirectory(localPath)
//                .setBranch("v1.4")
//                .call();
//        List<Ref> call = git.tagList().call();
//        for (Ref ref : call) {
//            System.out.println("Tag: " + ref + " " + ref.getName() + " " + ref.getObjectId().getName());
//        }
        System.out.println("Listing remote repository " + REMOTE_URL);
        Collection<Ref> refs = Git.lsRemoteRepository()
                .setHeads(true)
                .setTags(true)
                .setRemote(REMOTE_URL)
                .call();

        for (Ref ref : refs) {
//            if (ref.getName().startsWith("refs/tags/"))
                System.out.println("Ref: " + ref.getClass().getName() + "    Name: " + ref.getName());
        }

//        final Map<String, Ref> map = Git.lsRemoteRepository()
//                .setHeads(true)
//                .setTags(true)
//                .setRemote(REMOTE_URL)
//                .callAsMap();
//
//        System.out.println("As map");
//        for (Map.Entry<String, Ref> entry : map.entrySet()) {
//            System.out.println("Key: " + entry.getKey() + ", Ref: " + entry.getValue());
//        }
//
//        refs = Git.lsRemoteRepository()
//                .setRemote(REMOTE_URL)
//                .call();
//
//        System.out.println("All refs");
//        for (Ref ref : refs) {
//            System.out.println("Ref: " + ref);
//        }
    }
}

package com.anyun.common.service.git;

import java.util.List;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 17/06/2017
 */
public interface GitService {
    List<String> getAllRefTags() throws Exception;

    void pullServiceWithTag(String tag) throws Exception;

    void autoPullService() throws Exception;
}

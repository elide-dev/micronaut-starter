/*
 * Copyright 2017-2021 original authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.micronaut.starter.feature.awssecretsmanager;

import io.micronaut.starter.application.generator.GeneratorContext;
import io.micronaut.starter.build.dependencies.Dependency;
import io.micronaut.starter.feature.distributedconfig.DistributedConfigFeature;
import jakarta.inject.Singleton;
import java.util.Map;

/**
 * @see <a href="https://micronaut-projects.github.io/micronaut-aws/latest/guide/#distributedconfigurationsecretsmanager">Micronaut AWS Secrets Manager</a>
 * @author Sergio del Amo
 * @since 3.0.0
 */
@Singleton
public class AwsSecretsManager implements DistributedConfigFeature {
    @Override
    public String getTitle() {
        return "AWS Secrets Manager";
    }

    @Override
    public String getName() {
        return "aws-secrets-manager";
    }

    @Override
    public String getDescription() {
        return "Adds support for Distributed Configuration with AWS Secrets Manager";
    }

    @Override
    public void apply(GeneratorContext generatorContext) {
        generatorContext.addDependency(Dependency.builder()
                .groupId("io.micronaut.aws")
                .artifactId("micronaut-aws-secretsmanager")
                .compile());
        Map<String, Object> config = populateBootstrapForDistributedConfiguration(generatorContext);
        config.put("aws.client.system-manager.parameterstore.enabled", true);
    }

    @Override
    public String getMicronautDocumentation() {
        return "https://micronaut-projects.github.io/micronaut-aws/latest/guide/#distributedconfigurationsecretsmanager";
    }

    @Override
    public String getThirdPartyDocumentation() {
        return "https://aws.amazon.com/secrets-manager/";
    }
}

package io.micronaut.starter.feature.coherence

import io.micronaut.starter.BeanContextSpec
import io.micronaut.starter.BuildBuilder
import io.micronaut.starter.feature.config.Yaml
import io.micronaut.starter.fixture.CommandOutputFixture
import io.micronaut.starter.options.BuildTool
import io.micronaut.starter.options.Language
import spock.lang.Unroll

class CoherenceSessionSpec extends BeanContextSpec implements CommandOutputFixture {

    void 'test readme.md with feature coherence-session contains links to micronaut docs'() {
        when:
        def output = generate(['coherence-session'])
        def readme = output["README.md"]

        then:
        readme
        readme.contains("https://micronaut-projects.github.io/micronaut-coherence/latest/guide/#coherenceHttpSessions")
        readme.contains("https://coherence.java.net/")
    }

    void 'test configuration with feature coherence-session'() {
        when:
        Map<String, String> output = generate([Yaml.NAME, 'coherence-session'])
        String configuration = output['src/main/resources/application.yml']

        then:
        configuration
        configuration.contains("""
  session:
    http:
      coherence:
        enabled: true
""")
    }

    @Unroll
    void 'test gradle coherence-session feature for language=#language'(Language language) {
        when:
        String template = new BuildBuilder(beanContext, BuildTool.GRADLE)
                .language(language)
                .features(['coherence-session'])
                .render()

        then:
        template.contains('implementation("io.micronaut.coherence:micronaut-coherence")')
        template.contains('implementation("io.micronaut.coherence:micronaut-coherence-session")')
        template.contains('implementation("com.oracle.coherence.ce:coherence:${coherenceVersion}")')

        where:
        language << Language.values().toList()
    }
    @Unroll
    void 'test maven coherence-session feature for language=#language'(Language language) {
        when:
        String template = new BuildBuilder(beanContext, BuildTool.MAVEN)
                .language(language)
                .features(['coherence-session'])
                .render()

        then:
        template.contains("""
    <dependency>
      <groupId>io.micronaut.coherence</groupId>
      <artifactId>micronaut-coherence</artifactId>
      <scope>compile</scope>
    </dependency>
""")
        template.contains("""
    <dependency>
      <groupId>com.oracle.coherence.ce</groupId>
      <artifactId>coherence</artifactId>
      <version>\${coherence.version}</version>
      <scope>compile</scope>
    </dependency>
""")
        template.contains("""
    <dependency>
      <groupId>io.micronaut.coherence</groupId>
      <artifactId>micronaut-coherence-session</artifactId>
      <scope>compile</scope>
    </dependency>
""")
        where:
        language << Language.values().toList()
    }
}

package kg.dpa.gov.evaluation.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.AbstractMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.Map;

//@Configuration
public class ThymeleafConfig implements WebMvcConfigurer {

//    @Bean
//    public MessageSource messageSource() {
//        String yamlBaseName = "messages/messages";
//        String encoding = "UTF-8";
//        return new YamlMessageSource(yamlBaseName, encoding);
//    }
//
//    @Bean
//    public LocaleResolver localeResolver() {
//        CookieLocaleResolver resolver = new CookieLocaleResolver();
//        resolver.setDefaultLocale(new Locale("ru")); // Установите язык по умолчанию
//        resolver.setCookieName("lang"); // Название cookie для хранения языка
//        resolver.setCookieMaxAge(6666); // Время жизни cookie (в секундах)
//        return resolver;
//    }
//
//    @Bean
//    public LocaleChangeInterceptor localeChangeInterceptor() {
//        LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
//        interceptor.setParamName("lang"); // Параметр запроса, который будет определять язык
//        return interceptor;
//    }
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(localeChangeInterceptor());
//    }
//
//
//    public class YamlMessageSource extends AbstractMessageSource {
//
//        private final Yaml yaml = new Yaml();
//        private final String yamlBaseName;
//        private final String encoding;
//
//        public YamlMessageSource(String yamlBaseName, String encoding) {
//            this.yamlBaseName = yamlBaseName;
//            this.encoding = encoding;
//        }
//
//        @Override
//        protected MessageFormat resolveCode(String code, Locale locale) {
//            String message = getMessage(code, locale);
//            if (message != null) {
//                return createMessageFormat(message, locale);
//            }
//            return null;
//        }
//
//        private String getMessage(String code, Locale locale) {
//            String yamlFile = resolveYamlFile(locale);
//            try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(yamlFile)) {
//                if (inputStream != null) {
//                    Map<String, Object> yamlData = yaml.load(inputStream);
//                    String message = getMessageFromYamlData(yamlData, code);
//                    if (message != null) {
//                        return message;
//                    }
//                }
//            } catch (IOException e) {
//                // Обработка ошибки загрузки файла YAML
//                e.printStackTrace();
//            }
//            return null;
//        }
//
//        private String resolveYamlFile(Locale locale) {
//            String language = locale.getLanguage();
//            String country = locale.getCountry();
//            String variant = locale.getVariant();
//            StringBuilder sb = new StringBuilder(yamlBaseName);
//            if (!language.isEmpty()) {
//                sb.append('_').append(language);
//            }
//            if (!country.isEmpty()) {
//                sb.append('_').append(country);
//            }
//            if (!variant.isEmpty()) {
//                sb.append('_').append(variant);
//            }
//            sb.append(".yml");
//            return sb.toString();
//        }
//
//        private String getMessageFromYamlData(Map<String, Object> yamlData, String code) {
//            String[] codeParts = code.split("\\.");
//            Map<String, Object> currentData = yamlData;
//            for (String part : codeParts) {
//                if (currentData.containsKey(part)) {
//                    Object value = currentData.get(part);
//                    if (value instanceof String) {
//                        return (String) value;
//                    } else if (value instanceof Map) {
//                        currentData = (Map<String, Object>) value;
//                    }
//                } else {
//                    break;
//                }
//            }
//            return null;
//        }
//
//        public MessageFormat createMessageFormat(String message, Locale locale) {
//            if (message != null) {
//                return new MessageFormat(message, locale);
//            }
//            return null;
//        }
//    }
}


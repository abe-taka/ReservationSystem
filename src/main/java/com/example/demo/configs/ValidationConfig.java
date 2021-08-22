package com.example.demo.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//バリデーション
@Configuration
public class ValidationConfig implements WebMvcConfigurer {

	public org.springframework.validation.Validator getValidator() {
		LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
		// メッセージファイルを読込むための設定を記載
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		// ファイル名を「ValidationMessages」に設定
		messageSource.setBasename("classpath:ValidationMessages");
		// 文字コードをUTF-8にする
		messageSource.setDefaultEncoding("UTF-8");
		validator.setValidationMessageSource(messageSource);
		return validator;
	}
}
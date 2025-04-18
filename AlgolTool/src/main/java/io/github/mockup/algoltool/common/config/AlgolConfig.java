package io.github.mockup.algoltool.common.config;

import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.github.mockup.algoltool.common.constants.AlgolToolConst;
import lombok.Data;

@Component
@Data
public class AlgolConfig {
	public AlgolConfig(@Value("${algol.secret-dir:${user.home}/.algol/secret}") String secretDir) {
	    this.secretDir = secretDir;
	}

	private String secretDir;

	public String getSecretKey() {
		return Path.of(this.getSecretDir(), AlgolToolConst.Encrypt.KEY_FILENAME).toString();
	}

	public String getSecretIV() {
		return Path.of(this.getSecretDir(), AlgolToolConst.Encrypt.IV_FILENAME).toString();
	}

	public String getPublic() {
		return Path.of(this.getSecretDir(), AlgolToolConst.JWT.PUBLIC_FILENAME).toString();
	}

	public String getPrivate() {
		return Path.of(this.getSecretDir(), AlgolToolConst.JWT.PRIVATE_FILENAME).toString();
	}
}
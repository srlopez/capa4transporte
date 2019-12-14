REM Contraseña 123456

keytool -genkeypair -alias dam -keyalg RSA -validity 7 -keystore keystore
keytool -export -alias dam -keystore keystore -rfc -file micertificado.cer
keytool -import -alias dam -file micertificado.cer -keystore truststore
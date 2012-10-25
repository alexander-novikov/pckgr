package com.ximad.apkpackager.servlets;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import com.ximad.apkpackager.ServerConsts;
import com.ximad.apkpackager.ant.AntBuilder;
import com.ximad.apkpackager.ant.SignedAntBuilder;
import com.ximad.apkpackager.apk.config.ConfigFactory;
import com.ximad.apkpackager.apk.config.IConfigBuilder;
import com.ximad.apkpackager.apk.config.ISignerConfig;
import com.ximad.apkpackager.utils.FileUtils;
import com.ximad.apkpackager.utils.IUploadListener;
import com.ximad.apkpackager.utils.StreamUtils;
import com.ximad.apkpackager.utils.UploadUtil;

/**
 * Servlet implementation class GenerateApk
 */
@WebServlet(name = "generated.apk", urlPatterns = { "/generated.apk" })
public class GenerateApk extends HttpServlet implements
		IUploadListener<IConfigBuilder> {
	private static final long serialVersionUID = 1L;

	private static final String PREFIX_TEMP_DIRECTORY = "/temp/temp";
	private static final String HEADER_NAME_CONDENT_DISCRIPTION = "Content-Disposition";
	private static final String HEADER_VALUE_FILE_NAME = "attachment; filename=\"%s\"";

	private Random random = new Random();

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		IConfigBuilder configBuilder = null;
		ISignerConfig signerConfig = null;
		try {

			boolean isMultipart = ServletFileUpload.isMultipartContent(request);
			if (!isMultipart) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST);
				return;
			}

			signerConfig = ConfigFactory.createSigner();

			configBuilder = ConfigFactory.createDefaultConfig(createTempDirectory());
			UploadUtil.initParameters(configBuilder, request,
					configBuilder.getTempGlobalDirectory(), this);
			ConfigFactory.initParametrConfigFromApkFile(configBuilder);

			AntBuilder antBuilder = new SignedAntBuilder(configBuilder,
					signerConfig);

			antBuilder.runAnt();
			resposeApk(configBuilder, response);
		} finally {
			if (configBuilder != null) {
				FileUtils.recursiveDelete(configBuilder.getTempGlobalDirectory());
			}
		}

	}

	protected void resposeApk(final IConfigBuilder configBuilder,
			final HttpServletResponse response) throws IOException {
		response.setContentType(ServerConsts.TYPE_ANDROID_PACKAGE);
		File apk = configBuilder.getResultFile();
		int size = (int) apk.length();
		response.setContentLength(size);

		response.setHeader(HEADER_NAME_CONDENT_DISCRIPTION,
				String.format(HEADER_VALUE_FILE_NAME, apk.getName()));
		FileInputStream fileInputStream = null;
		OutputStream outputStream = null;
		try {

			fileInputStream = new FileInputStream(apk);
			outputStream = response.getOutputStream();
			StreamUtils.copyToStream(fileInputStream, outputStream);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			fileInputStream.close();
			outputStream.close();
		}

	}

	protected File createTempDirectory() {
		File tempDirectory = null;

		do {
			String path = getServletContext().getRealPath(
					PREFIX_TEMP_DIRECTORY + random.nextInt());
			tempDirectory = new File(path);
		} while (tempDirectory.exists());

		tempDirectory.mkdirs();
		return tempDirectory;

	}

	@Override
	public boolean onInitParameter(IConfigBuilder configBuilder, String name,
			String value) {
		if (ServerConsts.PARAMETER_USER_ID.equals(name)) {
			configBuilder.setUserId(value);
			return true;
		}
		
		return false;
	}

	@Override
	public boolean onInitFileParameter(IConfigBuilder configBuilder,
			FileItem item) {
		if (ServerConsts.PARAMETER_UPLOAD_FILE_APK.equals(item.getFieldName())) {
			configBuilder.setApkFile(UploadUtil.uploadFile(item,
					configBuilder.getTempGlobalDirectory()));
			return true;
		}

		return false;
	}

}

/**
 * Copyright (C) 2016 - 2017 youtongluan.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 		http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.yx.http;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.yx.http.handler.HttpHandlerChain;
import org.yx.http.handler.HttpInfo;
import org.yx.http.handler.WebContext;
import org.yx.log.Log;

/**
 * 
 * @author Administrator
 */
@SumkServlet(value = { "/upload/*" }, loadOnStartup = -1)
public class UploadServer extends AbstractHttpServer {

	private static final long serialVersionUID = 1L;
	final static String MULTI = "multipart/form-data";

	@Override
	protected void handle(String act, HttpInfo info, HttpServletRequest req, HttpServletResponse resp)
			throws Exception {
		if (req.getContentType() == null || !req.getContentType().startsWith(MULTI)) {
			Log.get(this.getClass()).error("the MIME of act is " + MULTI + ",not " + req.getContentType());
			return;
		}
		if (info.getUpload() == null) {

			Log.get(this.getClass()).error(act + " has error type, it must be have @Upload");
			return;
		}
		WebContext wc = new WebContext(act, info, req, resp);
		HttpHandlerChain.upload.handle(wc);
	}
}

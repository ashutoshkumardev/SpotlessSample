/*
 * Copyright 2016-2021 DiffPlug
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.diffplug.spotless.maven.generic;

import org.junit.jupiter.api.Test;

import com.diffplug.spotless.maven.MavenIntegrationHarness;

class ReplaceRegexTest extends MavenIntegrationHarness {

	@Test
	void fromContent() throws Exception {
		writePomWithFormatSteps(
				"<replaceRegex>",
				"  <name>Greetings to Mars</name>",
				"  <searchRegex>(hello) w[a-z]{3}d</searchRegex>",
				"  <replacement>$1 mars</replacement>",
				"</replaceRegex>");

		runTest("hello world", "hello mars");
	}

	private void runTest(String sourceContent, String targetContent) throws Exception {
		String path = "src/main/java/test.java";
		setFile(path).toContent(sourceContent);
		mavenRunner().withArguments("spotless:apply").runNoError();
		assertFile(path).hasContent(targetContent);
	}
}

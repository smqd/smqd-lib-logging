// Copyright 2019 UANGEL
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.thing2x.smqd.logging

import org.scalatest.flatspec.AnyFlatSpec

class SourcePositionAwareTest extends AnyFlatSpec with SourcePositionAware {

  "SourcePositionAware" should "know the current position" in {
    assert( LINE == 22)
    assert( FILE == "SourcePositionAwareTest.scala")
    assert(s"$FILE $LINE" == "SourcePositionAwareTest.scala 24")
  }

}

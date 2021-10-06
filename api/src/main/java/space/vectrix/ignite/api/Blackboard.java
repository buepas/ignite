/*
 * This file is part of Ignite, licensed under the MIT License (MIT).
 *
 * Copyright (c) vectrix.space <https://vectrix.space/>
 * Copyright (c) contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package space.vectrix.ignite.api;

import com.google.common.reflect.TypeToken;
import org.checkerframework.checker.nullness.qual.MonotonicNonNull;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import space.vectrix.ignite.api.util.BlackboardMap;

import java.nio.file.Path;
import java.util.List;

/**
 * Provides static access to properties Ignite makes use of.
 *
 * @since 0.5.0
 */
@SuppressWarnings("UnstableApiUsage")
public final class Blackboard {
  private static final BlackboardMap BLACKBOARD = new BlackboardMap();

  public static final BlackboardMap.@NonNull Key<Boolean>      DEBUG                        = key("ignite.debug", TypeToken.of(Boolean.class));

  public static final BlackboardMap.@NonNull Key<List<String>> LAUNCH_ARGUMENTS             = key("ignite.launch.arguments", new TypeToken<List<String>>() {});
  public static final BlackboardMap.@NonNull Key<String>       LAUNCH_SERVICE               = key("ignite.launch.service", TypeToken.of(String.class));
  public static final BlackboardMap.@NonNull Key<Path>         LAUNCH_JAR                   = key("ignite.launch.jar", TypeToken.of(Path.class));
  public static final BlackboardMap.@NonNull Key<String>       LAUNCH_TARGET                = key("ignite.launch.target", TypeToken.of(String.class));

  public static final BlackboardMap.@NonNull Key<Path> MOD_DIRECTORY_PATH                   = key("ignite.mod.directory", TypeToken.of(Path.class));
  public static final BlackboardMap.@NonNull Key<Path> CONFIG_DIRECTORY_PATH                = key("ignite.config.directory", TypeToken.of(Path.class));
  public static final BlackboardMap.@NonNull Key<Boolean> EXCLUDE_MIXIN_FROM_TRANSFORMATION = key("ignite.exclude.mixinPath", TypeToken.of(Boolean.class));

  /**
   * Returns the property for the specified {@link BlackboardMap.Key}, if it
   * exists, otherwise returns {@code null}.
   *
   * @param key the property key
   * @param <T> the property type
   * @return the property, if present
   * @since 0.5.0
   */
  public static <T> @MonotonicNonNull T getProperty(final BlackboardMap.@NonNull Key<T> key) {
    return Blackboard.BLACKBOARD.get(key).orElse(null);
  }

  /**
   * Returns the property for the specified {@link BlackboardMap.Key}, if it
   * exists, otherwise returns the specified {@code defaultValue}.
   *
   * @param key the property key
   * @param defaultValue the default property
   * @param <T> the property type
   * @return the property
   * @since 0.5.0
   */
  public static <T> @NonNull T getProperty(final BlackboardMap.@NonNull Key<T> key, final @NonNull T defaultValue) {
    return Blackboard.BLACKBOARD.get(key).orElse(defaultValue);
  }

  /**
   * Associates the specified {@link BlackboardMap.Key} with the specified
   * {@code value}.
   *
   * @param key the property key
   * @param value the property
   * @param <T> the property type
   * @since 0.5.0
   */
  public static <T> void putProperty(final BlackboardMap.@NonNull Key<T> key, final @NonNull T value) {
    Blackboard.BLACKBOARD.put(key, value);
  }

  /**
   * Computes the value for the specified {@link BlackboardMap.Key}, storing
   * the specified {@code value} if an existing association is absent.
   *
   * @param key the property key
   * @param value the property
   * @param <T> the property type
   * @since 0.5.0
   */
  public static <T> void computeProperty(final BlackboardMap.@NonNull Key<T> key, final @Nullable T value) {
    Blackboard.BLACKBOARD.computeIfAbsent(key, k -> value);
  }

  /**
   * Returns a new {@link BlackboardMap.Key} with the specified {@code key}
   * and {@link TypeToken}.
   *
   * @param key the property key
   * @param type the property type token
   * @param <T> the property type
   * @return the property key
   * @since 0.5.0
   */
  public static <T> BlackboardMap.@NonNull Key<T> key(final @NonNull String key, final @NonNull TypeToken<T> type) {
    return BlackboardMap.Key.getOrCreate(Blackboard.BLACKBOARD, key, type.getRawType());
  }
}

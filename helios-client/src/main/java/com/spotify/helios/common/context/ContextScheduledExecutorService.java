/*
 * Copyright (c) 2014 Spotify AB.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.spotify.helios.common.context;

import java.util.concurrent.Callable;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import static com.spotify.helios.common.context.Context.makeContextCallable;
import static com.spotify.helios.common.context.Context.makeContextRunnable;

class ContextScheduledExecutorService extends ContextExecutorService
    implements ScheduledExecutorService {

  private final ScheduledExecutorService service;

  public ContextScheduledExecutorService(ScheduledExecutorService service) {
    super(service);
    this.service = service;
  }

  @Override
  public ScheduledFuture<?> schedule(Runnable command, long delay, TimeUnit unit) {
    return service.schedule(makeContextRunnable(command), delay, unit);
  }

  @Override
  public <V> ScheduledFuture<V> schedule(Callable<V> callable, long delay, TimeUnit unit) {
    return service.schedule(makeContextCallable(callable), delay, unit);
  }

  @Override
  public ScheduledFuture<?> scheduleAtFixedRate(Runnable command, long initialDelay, long period,
      TimeUnit unit) {
    return service.scheduleAtFixedRate(makeContextRunnable(command), initialDelay, period, unit);
  }

  @Override
  public ScheduledFuture<?> scheduleWithFixedDelay(Runnable command, long initialDelay, long delay,
      TimeUnit unit) {
    return service.scheduleWithFixedDelay(makeContextRunnable(command), initialDelay, delay, unit);
  }
}

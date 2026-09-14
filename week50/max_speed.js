function solution(city, road) {
  var answer = [];
  let [cityNumber, roadNumber] = [city.length, road.length];

  // 카메라 제한 속도 최대가 1,000,000
  // 그보다 큰 값 = 제한 없음
  const INF = 1000000001;

  // 좌표 -> 정점 번호
  const pointToVertex = new Map();

  // 정점 좌표
  const vx = [];
  const vy = [];

  // 해당 정점에 카메라가 있다면 제한속도
  // 없다면 INF
  const cameraLimit = [];

  // 각 도로 위에 존재하는 정점들
  const roadPoints = [...Array(roadNumber)].map(() => []);

  // 각 도시가 어떤 정점인지
  const cityVertex = Array(cityNumber);

  const getKey = (x, y) => {
    return `${x},${y}`;
  };

  // 좌표가 이미 정점이면 기존 정점 반환
  // 아니면 새 정점 생성
  const getVertex = (x, y) => {
    const key = getKey(x, y);

    if (pointToVertex.has(key)) {
      return pointToVertex.get(key);
    }

    const idx = vx.length;

    pointToVertex.set(key, idx);

    vx.push(x);
    vy.push(y);
    cameraLimit.push(INF);

    return idx;
  };

  const isHorizontal = (r) => {
    return r[1] === r[3];
  };

  // 점이 도로 위에 존재하는지
  const isPointOnRoad = (x, y, r) => {
    let [x1, y1, x2, y2] = r;

    if (y1 === y2) {
      return y === y1 && x1 <= x && x <= x2;
    }

    return x === x1 && y1 <= y && y <= y2;
  };

  // 두 도로의 교차점
  // 없다면 null
  const getCrossPoint = (ir, jr) => {
    let [ix1, iy1, ix2, iy2] = ir;
    let [jx1, jy1, jx2, jy2] = jr;

    const iHorizontal = iy1 === iy2;
    const jHorizontal = jy1 === jy2;

    // 둘 다 가로
    if (iHorizontal && jHorizontal) {
      if (iy1 !== jy1) {
        return null;
      }

      const left = Math.max(ix1, jx1);

      const right = Math.min(ix2, jx2);

      // 문제 조건상 두 도로는 최대 한 점에서만 만남
      if (left === right) {
        return [left, iy1];
      }

      return null;
    }

    // 둘 다 세로
    if (!iHorizontal && !jHorizontal) {
      if (ix1 !== jx1) {
        return null;
      }

      const bottom = Math.max(iy1, jy1);

      const top = Math.min(iy2, jy2);

      if (bottom === top) {
        return [ix1, bottom];
      }

      return null;
    }

    // i 가로 / j 세로
    if (iHorizontal) {
      if (ix1 <= jx1 && jx1 <= ix2 && jy1 <= iy1 && iy1 <= jy2) {
        return [jx1, iy1];
      }

      return null;
    }

    // i 세로 / j 가로
    if (jx1 <= ix1 && ix1 <= jx2 && iy1 <= jy1 && jy1 <= iy2) {
      return [ix1, jy1];
    }

    return null;
  };

  /*
        1. 카메라 정점 생성

        카메라가 같은 위치에 여러 개면
        가장 작은 제한속도만 저장
    */
  for (let i = 0; i < roadNumber; i++) {
    let [x1, y1, x2, y2, limit] = road[i];

    let cx = (x1 + x2) / 2;
    let cy = (y1 + y2) / 2;

    let v = getVertex(cx, cy);

    cameraLimit[v] = Math.min(cameraLimit[v], limit);

    roadPoints[i].push(v);
  }

  /*
        2. 도시 정점 생성

        도시가 여러 도로의 교차점에 있을 수도 있으므로
        해당 도시가 올라가 있는 모든 도로에 추가
    */
  for (let i = 0; i < cityNumber; i++) {
    let [x, y] = city[i];

    let v = getVertex(x, y);

    cityVertex[i] = v;

    for (let j = 0; j < roadNumber; j++) {
      if (isPointOnRoad(x, y, road[j])) {
        roadPoints[j].push(v);
      }
    }
  }

  /*
        3. 모든 도로 조합을 확인해서
        교차점 정점 생성
    */
  for (let i = 0; i < roadNumber; i++) {
    for (let j = i + 1; j < roadNumber; j++) {
      let crossPoint = getCrossPoint(road[i], road[j]);

      if (crossPoint === null) {
        continue;
      }

      let [x, y] = crossPoint;

      /*
                카메라 / 도시 / 다른 교차점과
                같은 좌표라면 getVertex에서
                같은 정점 번호를 사용
            */
      let v = getVertex(x, y);

      roadPoints[i].push(v);
      roadPoints[j].push(v);
    }
  }

  /*
        각 도로의 정점들을 실제 위치 순으로 정렬

        A ---- B ---- C ---- Camera ---- D

        이런 순서를 만든다.
    */

  let edgeNumber = 0;

  for (let i = 0; i < roadNumber; i++) {
    let points = roadPoints[i];

    if (isHorizontal(road[i])) {
      points.sort((a, b) => vx[a] - vx[b]);
    } else {
      points.sort((a, b) => vy[a] - vy[b]);
    }

    /*
            같은 위치의 정점이 여러 번 들어왔을 수 있음.

            예:
            도시이면서 교차점

            같은 정점 번호 제거
        */
    let write = 0;

    for (let read = 0; read < points.length; read++) {
      if (write > 0 && points[write - 1] === points[read]) {
        continue;
      }

      points[write++] = points[read];
    }

    points.length = write;

    edgeNumber += Math.max(0, points.length - 1);
  }

  /*
        인접 리스트

        정점이 최대 약 50만 개라
        dist[V][V]는 사용할 수 없음.

        메모리를 줄이기 위해
        typed array로 그래프 구성.
    */

  const vertexNumber = vx.length;

  const head = new Int32Array(vertexNumber);

  head.fill(-1);

  const to = new Int32Array(edgeNumber * 2);

  const next = new Int32Array(edgeNumber * 2);

  const weight = new Int32Array(edgeNumber * 2);

  let edgeIdx = 0;

  const addEdge = (u, v, w) => {
    to[edgeIdx] = v;
    weight[edgeIdx] = w;
    next[edgeIdx] = head[u];
    head[u] = edgeIdx++;

    to[edgeIdx] = u;
    weight[edgeIdx] = w;
    next[edgeIdx] = head[v];
    head[v] = edgeIdx++;
  };

  /*
        4. 각 도로에서
        실제로 인접한 정점끼리만 연결
    */

  for (let i = 0; i < roadNumber; i++) {
    let points = roadPoints[i];

    for (let j = 0; j + 1 < points.length; j++) {
      let u = points[j];
      let v = points[j + 1];

      /*
                예:

                A ---- Camera ---- B

                A-Camera 간선도 limit
                Camera-B 간선도 limit

                카메라 없는 경우 INF
            */
      let w = Math.min(cameraLimit[u], cameraLimit[v]);

      addEdge(u, v, w);
    }
  }

  /*
        최대 힙

        [현재까지 가능한 속도, 정점]
    */
  const heap = [];

  const heapPush = (heap, v) => {
    heap.push(v);

    let cur = heap.length - 1;

    while (cur > 0) {
      let parent = Math.floor((cur - 1) / 2);

      if (heap[parent][0] >= heap[cur][0]) {
        break;
      }

      [heap[parent], heap[cur]] = [heap[cur], heap[parent]];

      cur = parent;
    }
  };

  const heapPop = (heap) => {
    if (heap.length === 1) {
      return heap.pop();
    }

    const ret = heap[0];

    heap[0] = heap.pop();

    let cur = 0;

    while (true) {
      let left = cur * 2 + 1;

      let right = cur * 2 + 2;

      let nextIdx = cur;

      if (left < heap.length && heap[left][0] > heap[nextIdx][0]) {
        nextIdx = left;
      }

      if (right < heap.length && heap[right][0] > heap[nextIdx][0]) {
        nextIdx = right;
      }

      if (nextIdx === cur) {
        break;
      }

      [heap[cur], heap[nextIdx]] = [heap[nextIdx], heap[cur]];

      cur = nextIdx;
    }

    return ret;
  };

  /*
        5. 변형 다익스트라

        best[v]
        =
        1번 도시 -> v까지 갈 때
        낼 수 있는 최대 일정 속도
    */

  const best = new Int32Array(vertexNumber);

  const start = cityVertex[0];

  best[start] = INF;

  heapPush(heap, [INF, start]);

  while (heap.length > 0) {
    let [curSpeed, cur] = heapPop(heap);

    if (best[cur] !== curSpeed) {
      continue;
    }

    for (let e = head[cur]; e !== -1; e = next[e]) {
      let nv = to[e];

      /*
                지금 경로에서 가능한 속도와
                새 간선의 제한속도 중 작은 값
            */
      let nextSpeed = Math.min(curSpeed, weight[e]);

      /*
                기존보다 더 빠른 속도로
                nv까지 갈 수 있을 때 갱신
            */
      if (best[nv] >= nextSpeed) {
        continue;
      }

      best[nv] = nextSpeed;

      heapPush(heap, [nextSpeed, nv]);
    }
  }

  /*
        6. 2 ~ n번 도시의 결과
    */
  for (let i = 1; i < cityNumber; i++) {
    let speed = best[cityVertex[i]];

    /*
            INF 그대로면
            카메라를 하나도 지나지 않고
            이동 가능한 경로가 있다는 뜻
        */
    if (speed === INF) {
      answer.push(0);
    } else {
      answer.push(speed);
    }
  }

  return answer;
}

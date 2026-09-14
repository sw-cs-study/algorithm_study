// 숫자 삽입은 O(1), D 연산은 O(N)으로 총 시간복잡도는 O(N^2).
// 힙 자료구조을 사용하면 D 연산에 걸리는 시간을 log(N)으로 줄일 수 있음. log(N) '=. 20
// 최소힙과 최대힙을 두 개 만들기. 0번 인덱스가 root. 이러면 최소 또는 최댓값을 삭제할 때, 동기화가 어려움.
// 어떤 데이터를 삭제했는지 저장하기.
// O(N*log(N))으로 가능

// 최소힙에서 최댓값을 알 수 있나? 리프 노드를 다 봐야 가능함. 최대 리프 노드 수는 4096보다 큰 5XXX 정도

// 리프노드를 매번 정렬?? 다 정렬하지 말고 제일 뒤에 있는 거를 최댓값으로 들고 가기.
// 리프노드들 비교하기
// 바로 앞의 리프노드가 같은 깊이인 경우 + 깊이가 하나 적은 경우, 더 큰 수라면 자리바꾸기
// push, pop 연산 시 정렬이 깨짐.

function solution(operations) {
  const isDeleted = new Set(); // 삭제한 데이터 idx 저장, idx는 operations의 인덱스를 따름

  // a > b 이면 true 반환
  const compare = (a, b) => {
    if (a[0] > b[0]) return true;
    return false;
  };

  const heapPush = (heap, v, isMin = true) => {
    let idx = heap.length;
    heap.push(v);
    if (idx === 0) return;
    let parent = Math.floor((idx - 1) / 2);

    while (idx > 0 && isMin === compare(heap[parent], heap[idx])) {
      [heap[parent], heap[idx]] = [heap[idx], heap[parent]];
      idx = parent;
      parent = Math.floor((idx - 1) / 2);
    }

    return;
  };

  const heapPop = (heap, isMin = true) => {
    if (heap.length === 0) return [null, null];
    if (heap.length === 1) return heap.pop();

    let ret = [...heap[0]];
    heap[0] = heap.pop();

    let idx = 0;
    let child = idx * 2 + 1;
    if (
      child + 1 < heap.length &&
      compare(heap[child], heap[child + 1]) === isMin
    )
      child++;

    while (child < heap.length && isMin === compare(heap[idx], heap[child])) {
      [heap[idx], heap[child]] = [heap[child], heap[idx]];
      idx = child;
      child = idx * 2 + 1;
      if (
        child + 1 < heap.length &&
        compare(heap[child], heap[child + 1]) === isMin
      )
        child++;
    }

    return ret;
  };

  const checkData = (callback) => {
    let [v, i] = callback();
    if (i === null) return [null, null];

    while (isDeleted.has(i)) {
      [v, i] = callback();
    }

    return [v, i];
  };

  let [minHeap, maxHeap] = [[], []];

  operations.forEach((line, i) => {
    let [op, number] = line.split(" ");
    number = Number(number);

    if (op === "I") {
      heapPush(minHeap, [number, i]);
      heapPush(maxHeap, [number, i], false);
    } else if (op === "D") {
      let _, id;
      if (number === 1) {
        [_, id] = checkData(() => heapPop(maxHeap, false));
      } else if (number === -1) {
        [_, id] = checkData(() => heapPop(minHeap));
      }
      if (id !== null) isDeleted.add(id);
    }
  });

  let [max, q] = checkData(() => heapPop(maxHeap, false));
  let [min, w] = checkData(() => heapPop(minHeap));

  if (max === null || min === null) return [0, 0];
  return [max, min];
}

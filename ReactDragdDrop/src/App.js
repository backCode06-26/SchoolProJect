import React, { useState } from 'react';
import { DragDropContext, Droppable, Draggable } from 'react-beautiful-dnd';
import { v4 as uuidv4 } from 'uuid';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faTrash } from '@fortawesome/free-solid-svg-icons';
import { faFlask } from '@fortawesome/free-solid-svg-icons';

import './App.css';

const App = () => {
  const [items1, setItems1] = useState([
    { id: '1', content: '비커' },
    { id: '2', content: '염산' },
    { id: '3', content: '황산' },
    { id: '4', content: '구연산' },
    { id: '5', content: '아세트산' },
    { id: '6', content: '물' },
    { id: '7', content: '탄산소다' },
    { id: '8', content: '나트륨 하이드록사이드' },
    { id: '9', content: '물' },
    { id: '10', content: 'BTB용액' },
  ]);

  const [items2, setItems2] = useState([]);
  const [isFind, setIsFind] = useState(false);
  const [color, setColor] = useState('black');
  const [temperature, setTemperature] = useState(20);

  const onDragEnd = (result) => {
    const { destination, source } = result;
  
    if (!destination) {
      return;
    }
  
    if (destination.droppableId === source.droppableId && source.index === destination.index) {
      return;
    }
  
    if (destination.droppableId !== source.droppableId) {
      const newItem = { ...items1[source.index], id: uuidv4() };
  
      if (!isFind) {
        if (newItem.content === '비커') {
          const newItems2 = [newItem, ...items2];
          setItems2(newItems2);
          setIsFind(true);
        } else {
          alert('비커를 먼저 가져와 주세요.');
          return;
        }
      } else {
        if ((newItem.content === '비커' || newItem.content === 'BTB용액') && items2.some(item => item.content === newItem.content)) {
          alert(`${newItem.content}는 이미 추가되었습니다.`);
          return;
        }
  
        const newItems2 = [...items2];
        newItems2.splice(destination.index, 0, newItem);
        setItems2(newItems2);
        calculatePh(newItems2); // 아이템을 추가한 후에 pH를 계산합니다.
      }
    }
  };
  
  const handleClickItem = (index) => {
    let clickedItem = items2[index];

    if (clickedItem) {
      let newItems2 = [...items2];
      newItems2.splice(index, 1);
      setItems2(newItems2);

      console.log(`클릭한 아이템: ${clickedItem.content}`);
      calculatePh(newItems2); // 아이템을 삭제한 후에 pH를 계산합니다.
    }
  };
  
  const createWater = (acid, base) => {
    if (acid > 0 && base > 0) {
      const water = Math.min(acid, base); // 산과 염기 중 더 작은 값으로 물의 양을 결정합니다.
      acid -= water; // 산의 양에서 물의 양을 뺍니다.
      base -= water; // 염기의 양에서 물의 양을 뺍니다.
      console.log(acid, base)
      
      const updatedTemperature = temperature + 4 * water; // 온도를 업데이트합니다.
      setTemperature(updatedTemperature);
    }
  };  
  
  const calculatePh = (items) => {
    const acids = ['염산', '황산', '구연산', '아세트산'];
    const bases = ['탄산소다', '나트륨 하이드록사이드'];
  
    let ph = 0;
    let acid = 0;
    let base = 0;
  
    items.forEach(item => {
      if (acids.includes(item.content)) {
        ph += 1;
        acid++;
      } else if (bases.includes(item.content)) {
        ph -= 1;
        base++;
      }
    });
    
    let color;
  
    if (ph > 0) {
      color = 'yellow';
    } else if (ph === 0) {
      color = 'green';
    } else {
      color = 'blue';
    }

    setColor(color);
    createWater(acid, base);

    const flaskIcon = document.querySelector('.fa_soild');
    
    if (items.some(item => item.content === 'BTB용액')) {
      flaskIcon.style.color = color;
    } else {
      flaskIcon.style.color = '';
    }
  
    console.log(`현재 pH: ${ph}`);
  };
  

  return (
    <DragDropContext onDragEnd={onDragEnd}>
      <div style={{ display: 'flex' }}>
        <Droppable droppableId="list1">
          {(provided, snapshot) => (
            <div
              ref={provided.innerRef}
              {...provided.droppableProps}
              className={`list select ${snapshot.isDraggingOver ? 'dragging' : ''}`}
            >
              <div className="search">
                <input type="text" placeholder="검색어 입력" />
                <img
                  src="https://s3.ap-northeast-2.amazonaws.com/cdn.wecode.co.kr/icon/search.png"
                  alt="search-icon"
                />
              </div>
              {items1.map((item, index) => (
                <Draggable key={item.id} draggableId={item.id} index={index}>
                  {(provided, snapshot) => (
                    <div
                      ref={provided.innerRef}
                      {...provided.draggableProps}
                      {...provided.dragHandleProps}
                      className={`item ${snapshot.isDragging ? 'dragging' : ''}`}
                    >
                      {item.content}
                    </div>
                  )}
                </Draggable>
              ))}
              {provided.placeholder}
            </div>
          )}
        </Droppable>

        <div className="wrap">
          <div className="view">
            <div className='fa_soild_contianer'>
              온도: {temperature}<br/>
              용액: btb용액<br/>
              색상: {color}
            </div>
            <FontAwesomeIcon className='fa_soild' icon={faFlask} />
          </div>

          <Droppable droppableId="list2">
            {(provided, snapshot) => {
              const itemCounts = items2.reduce((acc, item) => {
                acc[item.content] = (acc[item.content] || 0) + 1;
                return acc;
              }, {});

              const items2Unique = Array.from(new Set(items2.map(item => item.content)))
                .map(content => {
                  return items2.find(item => item.content === content)
                });

              return (
                <div
                  ref={provided.innerRef}
                  {...provided.droppableProps}
                  className={`list_production ${snapshot.isDraggingOver ? 'dragging' : ''}`}
                >
                  {items2Unique.map((item, index) => {
                    const count = itemCounts[item.content];

                    return (
                      <Draggable
                        key={item.id}
                        draggableId={item.id}
                        index={index}
                        isDragDisabled={true}
                      >
                        {(provided, snapshot) => (
                          <div
                            ref={provided.innerRef}
                            {...provided.draggableProps}
                            {...provided.dragHandleProps}
                            className={`item ${snapshot.isDragging ? 'dragging' : ''}`}
                            onClick={() => handleClickItem(index)}
                          >
                            {item.content} {count > 1 && `(${count})`}
                            <FontAwesomeIcon
                              icon={faTrash}
                              className="delete-icon"
                              onClick={() => handleClickItem(index)}
                            />
                          </div>
                        )}
                      </Draggable>
                    );
                  })}
                  {provided.placeholder}
                </div>
              );
            }}
          </Droppable>
        </div>
      </div>
    </DragDropContext>
  );
};

export default App;
